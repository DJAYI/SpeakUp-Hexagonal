package com.electiva.speakup.domain.model.answer;

import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerScorerTest {

    private static final double DELTA = 0.001;

    @Test
    void singleChoiceReturnsFullPercentageWhenOptionIsCorrect() {
        Question question = question(QuestionType.SINGLE_CHOICE,
                option(1001L, true),
                option(1002L, false));
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .optionId(1001L)
                .build();

        assertEquals(100.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void singleChoiceReturnsZeroWhenOptionIsIncorrect() {
        Question question = question(QuestionType.SINGLE_CHOICE,
                option(1001L, true),
                option(1002L, false));
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .optionId(1002L)
                .build();

        assertEquals(0.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void multipleChoiceReturnsPartialPercentageWhenSomeCorrectOptionsAreSelected() {
        Question question = question(QuestionType.MULTIPLE_CHOICE,
                option(1001L, true),
                option(1002L, true),
                option(1003L, true));
        MultpleOptionAnswer answer = MultpleOptionAnswer.builder()
                .optionIds(List.of(1001L, 1002L))
                .build();

        assertEquals(66.67, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void multipleChoiceReturnsFullPercentageWhenAllCorrectOptionsAreSelected() {
        Question question = question(QuestionType.MULTIPLE_CHOICE,
                option(1001L, true),
                option(1002L, true));
        MultpleOptionAnswer answer = MultpleOptionAnswer.builder()
                .optionIds(List.of(1001L, 1002L))
                .build();

        assertEquals(100.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void multipleChoiceDoesNotDiscountIncorrectSelections() {
        Question question = question(QuestionType.MULTIPLE_CHOICE,
                option(1001L, true),
                option(1002L, true),
                option(1003L, true));
        MultpleOptionAnswer answer = MultpleOptionAnswer.builder()
                .optionIds(List.of(1001L, 9999L))
                .build();

        assertEquals(33.33, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void multipleChoiceReturnsZeroWhenThereAreNoCorrectOptions() {
        Question question = question(QuestionType.MULTIPLE_CHOICE,
                option(1001L, false),
                option(1002L, false));
        MultpleOptionAnswer answer = MultpleOptionAnswer.builder()
                .optionIds(List.of(1001L))
                .build();

        assertEquals(0.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void freeTextReturnsFullPercentageWhenTextIsNotBlank() {
        Question question = question(QuestionType.FREE_TEXT);
        FreeTextAnswer answer = FreeTextAnswer.builder()
                .text("Mi respuesta")
                .build();

        assertEquals(100.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void freeTextReturnsZeroWhenTextIsBlank() {
        Question question = question(QuestionType.FREE_TEXT);
        FreeTextAnswer answer = FreeTextAnswer.builder()
                .text("   ")
                .build();

        assertEquals(0.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    @Test
    void returnsZeroWhenAnswerTypeDoesNotMatchQuestionType() {
        Question question = question(QuestionType.FREE_TEXT);
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .optionId(1001L)
                .build();

        assertEquals(0.0, AnswerScorer.percentage(question, answer), DELTA);
    }

    private Question question(QuestionType type, Option... options) {
        return Question.builder()
                .id(10L)
                .type(type)
                .options(List.of(options))
                .build();
    }

    private Option option(Long id, boolean correct) {
        return Option.builder()
                .id(id)
                .correct(correct)
                .build();
    }
}
