package com.electiva.speakup.application.port.out;

import com.electiva.speakup.domain.model.answer.Answer;

import java.util.List;

public interface AnswerRepositoryPort {

    public void save(Answer answer);

    public List<Answer> findAnswersByQuestionId(Long questionId);
}
