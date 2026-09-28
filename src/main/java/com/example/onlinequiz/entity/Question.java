package com.example.onlinequiz.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "questions")
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "quiz_id", nullable = false) private Quiz quiz;
    @Column(nullable = false, length = 1500) private String text;
    @ElementCollection @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text", nullable = false, length = 500) @OrderColumn(name = "option_order") private List<String> options = new ArrayList<>();
    @Column(nullable = false) private Integer correctOptionIndex;
    @Column(nullable = false) private Integer points;
    protected Question() {}
    public Question(String text, List<String> options, Integer correctOptionIndex, Integer points) { this.text = text; this.options = new ArrayList<>(options); this.correctOptionIndex = correctOptionIndex; this.points = points; }
    void setQuiz(Quiz quiz) { this.quiz = quiz; }
    public Long getId() { return id; }
    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public Integer getCorrectOptionIndex() { return correctOptionIndex; }
    public Integer getPoints() { return points; }
}
