package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultipleOptionAnswerResponse extends AnswerResponse {
    private List<Long> optionIds;
}
