package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeTextAnswerRequest extends AnswerRequest {
    @NotBlank(message = "text must not be blank")
    private String text;
}
