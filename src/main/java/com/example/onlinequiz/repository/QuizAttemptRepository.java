package com.example.onlinequiz.repository;

import com.example.onlinequiz.entity.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    boolean existsByQuizIdAndStudentNameIgnoreCase(Long quizId, String studentName);
    List<QuizAttempt> findByQuizIdOrderByStartedAtDesc(Long quizId);
}
