package com.electiva.speakup.application.port.in;

import com.electiva.speakup.application.result.AnswerVerification;

import java.util.List;

public interface VerifyAnswersUseCase {

    List<AnswerVerification> verifyByCategory(Long categoryId);

    List<AnswerVerification> verifyByQuestion(Long questionId);
}
