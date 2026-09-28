package com.example.onlinequiz.dto;

import com.example.onlinequiz.entity.QuizAttempt;
import java.time.Instant;

public record AttemptView(Long id, Long quizId, String studentName, Instant startedAt, Instant submittedAt, int score, int totalPoints) {
    public static AttemptView from(QuizAttempt attempt) {
        return new AttemptView(attempt.getId(), attempt.getQuiz().getId(), attempt.getStudentName(), attempt.getStartedAt(), attempt.getSubmittedAt(), attempt.getScore(), attempt.getTotalPoints());
    }
}
