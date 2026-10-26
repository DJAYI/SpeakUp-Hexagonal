package com.electiva.speakup.infrastructure.adapter.in.http.dto;

import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.QuestionType;

import java.util.List;

public record CreateQuestionRequest(
        String statement,
        QuestionType type,
        List<Option> options
) {
}
