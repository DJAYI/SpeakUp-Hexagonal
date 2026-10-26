package com.electiva.speakup.domain.validation;

import com.electiva.speakup.domain.exception.InvalidAnswerTypeException;
import com.electiva.speakup.domain.exception.QuestionAlreadyAnsweredException;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import com.electiva.speakup.domain.model.answer.MultpleOptionAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnswerValidatorTest {

    @Test
    void acceptsFreeTextAnswerForFreeTextQuestion() {
        Question question = question(QuestionType.FREE_TEXT);
        Answer answer = FreeTextAnswer.builder()
                .questionId(question.getId())
                .text("Hola")
                .build();

        assertDoesNotThrow(() -> AnswerValidator.validate(question, answer, List.of()));
    }

    @Test
    void acceptsSingleOptionAnswerForSingleChoiceQuestion() {
        Question question = question(QuestionType.SINGLE_CHOICE);
        Answer answer = SingleOptionAnswer.builder()
                .questionId(question.getId())
                .optionId(1001L)
                .build();

        assertDoesNotThrow(() -> AnswerValidator.validate(question, answer, List.of()));
    }

    @Test
    void acceptsMultipleOptionAnswerForMultipleChoiceQuestion() {
        Question question = question(QuestionType.MULTIPLE_CHOICE);
        Answer answer = MultpleOptionAnswer.builder()
                .questionId(question.getId())
                .optionIds(List.of(1001L, 1002L))
                .build();

        assertDoesNotThrow(() -> AnswerValidator.validate(question, answer, List.of()));
    }

    @Test
    void rejectsAnswerThatDoesNotMatchQuestionType() {
        Question question = question(QuestionType.FREE_TEXT);
        Answer answer = SingleOptionAnswer.builder()
                .questionId(question.getId())
                .optionId(1001L)
                .build();

        assertThrows(InvalidAnswerTypeException.class,
                () -> AnswerValidator.validate(question, answer, List.of()));
    }

    @Test
    void rejectsAnswerWhenQuestionAlreadyHasAnAnswer() {
        Question question = question(QuestionType.FREE_TEXT);
        Answer existingAnswer = FreeTextAnswer.builder()
                .id(1L)
                .questionId(question.getId())
                .text("Primera")
                .build();
        Answer newAnswer = FreeTextAnswer.builder()
                .questionId(question.getId())
                .text("Segunda")
                .build();

        assertThrows(QuestionAlreadyAnsweredException.class,
                () -> AnswerValidator.validate(question, newAnswer, List.of(existingAnswer)));
    }

    private Question question(QuestionType type) {
        return Question.builder()
                .id(10L)
                .type(type)
                .build();
    }
}
