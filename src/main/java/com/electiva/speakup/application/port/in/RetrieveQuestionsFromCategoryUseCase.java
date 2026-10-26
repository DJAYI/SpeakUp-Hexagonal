package com.electiva.speakup.application.port.in;

import com.electiva.speakup.domain.model.Question;

import java.util.List;

public interface RetrieveQuestionsFromCategoryUseCase {
    List<Question> retrieve(Long categoryId);
}
