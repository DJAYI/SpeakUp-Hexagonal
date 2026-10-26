package com.electiva.speakup.infrastructure.adapter.in.http.dto;

import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.QuestionType;

import java.util.List;

public record QuestionResponse(
        Long id,
        String statement,
        QuestionType type,
        Long categoryId,
        List<Option> options,
        double percentage
) {
}
