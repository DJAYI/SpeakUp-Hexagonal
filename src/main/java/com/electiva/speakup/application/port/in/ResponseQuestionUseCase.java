package com.electiva.speakup.application.port.in;

import com.electiva.speakup.domain.model.answer.Answer;

public interface ResponseQuestionUseCase {
    void respond(Long questionId, Answer answer);
}
