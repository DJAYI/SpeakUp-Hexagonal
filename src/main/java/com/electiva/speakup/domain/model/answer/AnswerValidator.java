package com.electiva.speakup.domain.model.answer;

import com.electiva.speakup.domain.exception.InvalidAnswerTypeException;
import com.electiva.speakup.domain.exception.QuestionAlreadyAnsweredException;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;

import java.util.List;

public final class AnswerValidator {

    private AnswerValidator() {
    }

    public static void validate(Question question, Answer answer, List<Answer> existingAnswers) {
        if (question == null) {
            throw new IllegalArgumentException("Question must not be null");
        }

        if (answer == null) {
            throw new IllegalArgumentException("Answer must not be null");
        }

        if (existingAnswers != null && !existingAnswers.isEmpty()) {
            throw new QuestionAlreadyAnsweredException(question.getId());
        }

        if (!matchesQuestionType(question.getType(), answer)) {
            throw new InvalidAnswerTypeException(question.getType(), answer.getClass().getSimpleName());
        }
    }

    private static boolean matchesQuestionType(QuestionType questionType, Answer answer) {
        if (questionType == null) {
            return false;
        }

        return switch (questionType) {
            case FREE_TEXT -> answer instanceof FreeTextAnswer;
            case SINGLE_CHOICE -> answer instanceof SingleOptionAnswer;
            case MULTIPLE_CHOICE -> answer instanceof MultpleOptionAnswer;
        };
    }
}
