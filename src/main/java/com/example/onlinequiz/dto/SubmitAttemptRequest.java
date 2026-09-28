package com.example.onlinequiz.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.Map;

public record SubmitAttemptRequest(@NotNull Map<@Positive Long, @NotNull Integer> answers) {}
