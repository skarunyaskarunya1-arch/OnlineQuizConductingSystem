package com.example.onlinequiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record CreateQuizRequest(
        @NotBlank @Size(max = 160) String title,
        @Size(max = 1000) String description,
        @NotNull @Min(1) @Max(480) Integer durationMinutes,
        @NotEmpty List<@Valid QuestionInput> questions) {
    public record QuestionInput(
            @NotBlank @Size(max = 1500) String text,
            @NotNull @Size(min = 2, max = 8) List<@NotBlank @Size(max = 500) String> options,
            @NotNull @Min(0) Integer correctOptionIndex,
            @NotNull @Min(1) Integer points) {}
}
