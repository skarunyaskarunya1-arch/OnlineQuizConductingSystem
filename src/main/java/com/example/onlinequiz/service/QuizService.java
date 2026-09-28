package com.example.onlinequiz.service;

import com.example.onlinequiz.dto.*;
import com.example.onlinequiz.entity.*;
import com.example.onlinequiz.exception.ConflictException;
import com.example.onlinequiz.exception.NotFoundException;
import com.example.onlinequiz.repository.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizzes;
    private final QuizAttemptRepository attempts;

    public QuizService(QuizRepository quizzes, QuizAttemptRepository attempts) { this.quizzes = quizzes; this.attempts = attempts; }

    @Transactional
    public QuizView create(CreateQuizRequest request) {
        Quiz quiz = new Quiz(request.title().trim(), request.description(), request.durationMinutes());
        for (var input : request.questions()) {
            if (input.correctOptionIndex() >= input.options().size()) throw new IllegalArgumentException("correctOptionIndex must refer to an existing option");
            quiz.addQuestion(new Question(input.text().trim(), input.options(), input.correctOptionIndex(), input.points()));
        }
        return QuizView.from(quizzes.save(quiz));
    }

    @Transactional(readOnly = true)
    public List<QuizView> listQuizzes() { return quizzes.findAll().stream().map(QuizView::from).toList(); }

    @Transactional(readOnly = true)
    public QuizView getQuiz(long id) { return QuizView.from(findQuiz(id)); }

    @Transactional
    public AttemptView startAttempt(long quizId, StartAttemptRequest request) {
        Quiz quiz = findQuiz(quizId);
        String student = request.studentName().trim();
        if (attempts.existsByQuizIdAndStudentNameIgnoreCase(quizId, student)) throw new ConflictException("This student has already attempted this quiz");
        int total = quiz.getQuestions().stream().mapToInt(Question::getPoints).sum();
        try {
            return AttemptView.from(attempts.save(new QuizAttempt(quiz, student, Instant.now(), total)));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("This student has already attempted this quiz");
        }
    }

    @Transactional
    public AttemptView submitAttempt(long attemptId, SubmitAttemptRequest request) {
        QuizAttempt attempt = attempts.findById(attemptId).orElseThrow(() -> new NotFoundException("Attempt " + attemptId + " was not found"));
        if (attempt.getSubmittedAt() != null) throw new ConflictException("This attempt has already been submitted");
        Instant now = Instant.now();
        if (now.isAfter(attempt.getStartedAt().plusSeconds(attempt.getQuiz().getDurationMinutes() * 60L)))
            throw new ConflictException("The time limit for this quiz has expired");
        var questions = attempt.getQuiz().getQuestions();
        var ids = questions.stream().map(Question::getId).collect(java.util.stream.Collectors.toSet());
        if (!ids.containsAll(request.answers().keySet())) throw new IllegalArgumentException("Answers contain a question that does not belong to this quiz");
        int score = questions.stream().filter(q -> request.answers().containsKey(q.getId()) && request.answers().get(q.getId()).equals(q.getCorrectOptionIndex()))
                .mapToInt(Question::getPoints).sum();
        attempt.submit(score, now);
        return AttemptView.from(attempt);
    }

    @Transactional(readOnly = true)
    public List<AttemptView> getResults(long quizId) {
        findQuiz(quizId);
        return attempts.findByQuizIdOrderByStartedAtDesc(quizId).stream().map(AttemptView::from).toList();
    }

    private Quiz findQuiz(long id) { return quizzes.findById(id).orElseThrow(() -> new NotFoundException("Quiz " + id + " was not found")); }
}
