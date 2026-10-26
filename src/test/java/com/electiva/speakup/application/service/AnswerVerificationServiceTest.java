package com.electiva.speakup.application.service;

import com.electiva.speakup.application.exception.QuestionNotFoundException;
import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.application.result.AnswerVerification;
import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.MultpleOptionAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerVerificationServiceTest {

    @Test
    void verifyByQuestionReturnsPercentageForStoredAnswer() {
        Question question = Question.builder()
                .id(10L)
                .type(QuestionType.SINGLE_CHOICE)
                .options(List.of(Option.builder().id(1001L).correct(true).build()))
                .build();
        Answer answer = SingleOptionAnswer.builder()
                .id(5L)
                .questionId(10L)
                .optionId(1001L)
                .build();

        AnswerVerificationService service = new AnswerVerificationService(
                questionRepository(List.of(question), question),
                answerRepository(Map.of(10L, List.of(answer))));

        List<AnswerVerification> result = service.verifyByQuestion(10L);

        assertEquals(List.of(new AnswerVerification(10L, 5L, 100.0)), result);
    }

    @Test
    void verifyByQuestionThrowsWhenQuestionDoesNotExist() {
        AnswerVerificationService service = new AnswerVerificationService(
                questionRepository(List.of(), null),
                answerRepository(Map.of()));

        assertThrows(QuestionNotFoundException.class, () -> service.verifyByQuestion(99L));
    }

    @Test
    void verifyByCategoryReturnsVerificationsForAllAnsweredQuestions() {
        Question singleChoiceQuestion = Question.builder()
                .id(10L)
                .type(QuestionType.SINGLE_CHOICE)
                .options(List.of(Option.builder().id(1001L).correct(true).build()))
                .build();
        Question multipleChoiceQuestion = Question.builder()
                .id(11L)
                .type(QuestionType.MULTIPLE_CHOICE)
                .options(List.of(
                        Option.builder().id(2001L).correct(true).build(),
                        Option.builder().id(2002L).correct(true).build()))
                .build();
        Answer incorrectSingleAnswer = SingleOptionAnswer.builder()
                .id(5L)
                .questionId(10L)
                .optionId(9999L)
                .build();
        Answer partialMultipleAnswer = MultpleOptionAnswer.builder()
                .id(6L)
                .questionId(11L)
                .optionIds(List.of(2001L))
                .build();

        AnswerVerificationService service = new AnswerVerificationService(
                questionRepository(List.of(singleChoiceQuestion, multipleChoiceQuestion), null),
                answerRepository(Map.of(
                        10L, List.of(incorrectSingleAnswer),
                        11L, List.of(partialMultipleAnswer))));

        List<AnswerVerification> result = service.verifyByCategory(1L);

        assertEquals(List.of(
                new AnswerVerification(10L, 5L, 0.0),
                new AnswerVerification(11L, 6L, 50.0)), result);
    }

    @Test
    void verifyByCategoryReturnsEmptyWhenNoAnswersExist() {
        Question question = Question.builder()
                .id(10L)
                .type(QuestionType.FREE_TEXT)
                .build();

        AnswerVerificationService service = new AnswerVerificationService(
                questionRepository(List.of(question), null),
                answerRepository(Map.of()));

        assertTrue(service.verifyByCategory(1L).isEmpty());
    }

    private QuestionRepositoryPort questionRepository(List<Question> questions, Question questionById) {
        return new QuestionRepositoryPort() {
            @Override
            public Question save(Question question) {
                return question;
            }

            @Override
            public List<Question> findQuestionsByCategoryId(Long categoryId) {
                return questions;
            }

            @Override
            public Optional<Question> findById(Long questionId) {
                return Optional.ofNullable(questionById);
            }
        };
    }

    private AnswerRepositoryPort answerRepository(Map<Long, List<Answer>> answersByQuestionId) {
        return new AnswerRepositoryPort() {
            @Override
            public void save(Answer answer) {
            }

            @Override
            public List<Answer> findAnswersByQuestionId(Long questionId) {
                return answersByQuestionId.getOrDefault(questionId, List.of());
            }
        };
    }
}
