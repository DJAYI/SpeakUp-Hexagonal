package com.electiva.speakup.application.service;

import com.electiva.speakup.application.exception.QuestionNotFoundException;
import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnswerServiceTest {

    @Test
    void retrieveReturnsAnswersWhenQuestionExists() {
        Answer answer = FreeTextAnswer.builder()
                .id(1L)
                .questionId(10L)
                .text("Hola")
                .build();
        AnswerService service = new AnswerService(
                answerRepository(answer),
                questionRepository(Optional.of(Question.builder().id(10L).build()))
        );

        List<Answer> result = service.retrieve(10L);

        assertEquals(List.of(answer), result);
    }

    @Test
    void retrieveThrowsWhenQuestionDoesNotExist() {
        AnswerService service = new AnswerService(
                answerRepository(FreeTextAnswer.builder().build()),
                questionRepository(Optional.empty())
        );

        assertThrows(QuestionNotFoundException.class, () -> service.retrieve(99L));
    }

    private AnswerRepositoryPort answerRepository(Answer... answers) {
        return new AnswerRepositoryPort() {
            @Override
            public void save(Answer answer) {
            }

            @Override
            public List<Answer> findAnswersByQuestionId(Long questionId) {
                return List.of(answers);
            }
        };
    }

    private QuestionRepositoryPort questionRepository(Optional<Question> question) {
        return new QuestionRepositoryPort() {
            @Override
            public Question save(Question question) {
                return question;
            }

            @Override
            public List<Question> findQuestionsByCategoryId(Long categoryId) {
                return List.of();
            }

            @Override
            public Optional<Question> findById(Long questionId) {
                return question;
            }
        };
    }
}
