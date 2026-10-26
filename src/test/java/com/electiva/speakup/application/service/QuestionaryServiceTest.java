package com.electiva.speakup.application.service;

import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.CategoryRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.domain.exception.InvalidAnswerTypeException;
import com.electiva.speakup.domain.exception.QuestionAlreadyAnsweredException;
import com.electiva.speakup.domain.model.Category;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionaryServiceTest {

    @Test
    void respondRejectsSecondAnswerForSameQuestion() {
        Question question = question(QuestionType.FREE_TEXT);
        Answer existingAnswer = FreeTextAnswer.builder()
                .id(1L)
                .questionId(question.getId())
                .text("Primera")
                .build();
        List<Answer> savedAnswers = new ArrayList<>();
        QuestionaryService service = service(question, List.of(existingAnswer), savedAnswers);

        assertThrows(QuestionAlreadyAnsweredException.class,
                () -> service.respond(question.getId(), FreeTextAnswer.builder().text("Segunda").build()));
        assertTrue(savedAnswers.isEmpty());
    }

    @Test
    void respondRejectsAnswerThatDoesNotMatchQuestionType() {
        Question question = question(QuestionType.FREE_TEXT);
        List<Answer> savedAnswers = new ArrayList<>();
        QuestionaryService service = service(question, List.of(), savedAnswers);

        assertThrows(InvalidAnswerTypeException.class,
                () -> service.respond(question.getId(), SingleOptionAnswer.builder().optionId(1001L).build()));
        assertTrue(savedAnswers.isEmpty());
    }

    private QuestionaryService service(Question question, List<Answer> existingAnswers, List<Answer> savedAnswers) {
        AnswerRepositoryPort answerRepositoryPort = new AnswerRepositoryPort() {
            @Override
            public void save(Answer answer) {
                savedAnswers.add(answer);
            }

            @Override
            public List<Answer> findAnswersByQuestionId(Long questionId) {
                return existingAnswers;
            }
        };

        QuestionRepositoryPort questionRepositoryPort = new QuestionRepositoryPort() {
            @Override
            public Question save(Question questionToSave) {
                return questionToSave;
            }

            @Override
            public List<Question> findQuestionsByCategoryId(Long categoryId) {
                return List.of();
            }

            @Override
            public Optional<Question> findById(Long questionId) {
                return Optional.of(question);
            }
        };

        CategoryRepositoryPort categoryRepositoryPort = new CategoryRepositoryPort() {
            @Override
            public Category save(Category category) {
                return category;
            }

            @Override
            public Optional<Category> findById(Long id) {
                return Optional.empty();
            }

            @Override
            public List<Category> findAll() {
                return List.of();
            }

            @Override
            public Optional<Category> findByName(String name) {
                return Optional.empty();
            }
        };

        return new QuestionaryService(answerRepositoryPort, questionRepositoryPort, categoryRepositoryPort);
    }

    private Question question(QuestionType type) {
        return Question.builder()
                .id(10L)
                .type(type)
                .build();
    }
}
