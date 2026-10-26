package com.electiva.speakup.infrastructure.adapter.in.http.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateCategoryRequest(

        @NotBlank
        @Length(min = 1, max = 50)
        String name,

        @Length(max = 255)
        String description
) {
}
