package com.electiva.speakup.application.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long questionId) {
        super("Question not found with id: " + questionId);
    }
}
