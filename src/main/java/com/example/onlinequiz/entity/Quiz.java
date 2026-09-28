package com.example.onlinequiz.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "quizzes")
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 160) private String title;
    @Column(length = 1000) private String description;
    @Column(nullable = false) private Integer durationMinutes;
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true) private List<Question> questions = new ArrayList<>();
    protected Quiz() {}
    public Quiz(String title, String description, Integer durationMinutes) { this.title = title; this.description = description; this.durationMinutes = durationMinutes; }
    public void addQuestion(Question question) { questions.add(question); question.setQuiz(this); }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public List<Question> getQuestions() { return questions; }
}
