package com.example.onlinequiz.controller;

import com.example.onlinequiz.dto.*;
import com.example.onlinequiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {
    private final QuizService service;
    public QuizController(QuizService service) { this.service = service; }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public QuizView create(@Valid @RequestBody CreateQuizRequest request) { return service.create(request); }

    @GetMapping
    public List<QuizView> list() { return service.listQuizzes(); }

    @GetMapping("/{quizId}")
    public QuizView get(@PathVariable long quizId) { return service.getQuiz(quizId); }

    @PostMapping("/{quizId}/attempts") @ResponseStatus(HttpStatus.CREATED)
    public AttemptView start(@PathVariable long quizId, @Valid @RequestBody StartAttemptRequest request) { return service.startAttempt(quizId, request); }

    @PostMapping("/attempts/{attemptId}/submit")
    public AttemptView submit(@PathVariable long attemptId, @Valid @RequestBody SubmitAttemptRequest request) { return service.submitAttempt(attemptId, request); }

    @GetMapping("/{quizId}/results")
    public List<AttemptView> results(@PathVariable long quizId) { return service.getResults(quizId); }
}
