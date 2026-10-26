package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleOptionAnswerRequest extends AnswerRequest {
    @NotEmpty(message = "optionIds must not be empty")
    private List<@NotNull(message = "optionIds elements must not be null") Long> optionIds;
}
