package com.example.onlinequiz.dto;

import com.example.onlinequiz.entity.Quiz;
import java.util.List;

public record QuizView(Long id, String title, String description, int durationMinutes, List<QuestionView> questions) {
    public static QuizView from(Quiz quiz) {
        return new QuizView(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getDurationMinutes(),
                quiz.getQuestions().stream().map(q -> new QuestionView(q.getId(), q.getText(), List.copyOf(q.getOptions()), q.getPoints())).toList());
    }
    public record QuestionView(Long id, String text, List<String> options, int points) {}
}
