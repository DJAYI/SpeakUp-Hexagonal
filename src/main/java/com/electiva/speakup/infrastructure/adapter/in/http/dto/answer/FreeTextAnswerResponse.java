package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeTextAnswerResponse extends AnswerResponse {
    private String text;
}
