package com.electiva.speakup.application.port.in;

import com.electiva.speakup.domain.model.Question;

public interface AddQuestionToCategoryUseCase {
    void add(Long categoryId, Question question);
}
