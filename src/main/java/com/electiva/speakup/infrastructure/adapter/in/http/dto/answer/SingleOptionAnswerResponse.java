package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleOptionAnswerResponse extends AnswerResponse {
    private Long optionId;
}
