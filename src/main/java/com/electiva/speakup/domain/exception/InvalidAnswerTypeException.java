package com.electiva.speakup.domain.exception;

import com.electiva.speakup.domain.model.QuestionType;

public class InvalidAnswerTypeException extends RuntimeException {

    public InvalidAnswerTypeException(QuestionType questionType, String answerType) {
        super("Answer type " + answerType + " does not match question type " + questionType);
    }
}
