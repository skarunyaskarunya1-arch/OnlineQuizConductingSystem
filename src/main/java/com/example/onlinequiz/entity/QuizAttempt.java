package com.example.onlinequiz.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "quiz_attempts", uniqueConstraints = @UniqueConstraint(name = "uk_attempt_quiz_student", columnNames = {"quiz_id", "student_name"}))
public class QuizAttempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "quiz_id", nullable = false) private Quiz quiz;
    @Column(name = "student_name", nullable = false, length = 160) private String studentName;
    @Column(nullable = false) private Instant startedAt;
    private Instant submittedAt;
    @Column(nullable = false) private Integer score;
    @Column(nullable = false) private Integer totalPoints;
    protected QuizAttempt() {}
    public QuizAttempt(Quiz quiz, String studentName, Instant startedAt, int totalPoints) { this.quiz = quiz; this.studentName = studentName; this.startedAt = startedAt; this.totalPoints = totalPoints; this.score = 0; }
    public void submit(int score, Instant submittedAt) { this.score = score; this.submittedAt = submittedAt; }
    public Long getId() { return id; }
    public Quiz getQuiz() { return quiz; }
    public String getStudentName() { return studentName; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getSubmittedAt() { return submittedAt; }
    public Integer getScore() { return score; }
    public Integer getTotalPoints() { return totalPoints; }
}
