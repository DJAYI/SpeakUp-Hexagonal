package com.electiva.speakup.application.port.in;

import com.electiva.speakup.domain.model.answer.Answer;

import java.util.List;

public interface RetrieveAnswersFromQuestionUseCase {

    List<Answer> retrieve(Long questionId);
}
