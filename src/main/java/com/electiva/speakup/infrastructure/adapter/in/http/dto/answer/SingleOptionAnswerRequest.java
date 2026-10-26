package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleOptionAnswerRequest extends AnswerRequest {
    @NotNull(message = "optionId must not be null")
    private Long optionId;
}
