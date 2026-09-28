package com.example.onlinequiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StartAttemptRequest(@NotBlank @Size(max = 160) String studentName) {}
