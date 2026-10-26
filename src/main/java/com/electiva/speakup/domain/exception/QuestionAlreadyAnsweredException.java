package com.electiva.speakup.domain.exception;

public class QuestionAlreadyAnsweredException extends RuntimeException {

    public QuestionAlreadyAnsweredException(Long questionId) {
        super("Question with id " + questionId + " already has an answer");
    }
}
