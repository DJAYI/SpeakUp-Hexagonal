package com.electiva.speakup.application.port.out;

import com.electiva.speakup.domain.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepositoryPort {

    public Question save(Question question);

    public List<Question> findQuestionsByCategoryId(Long categoryId);

    public Optional<Question> findById(Long questionId);
}
