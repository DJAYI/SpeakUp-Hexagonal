package com.electiva.speakup.application.service;

import com.electiva.speakup.application.port.in.AddQuestionToCategoryUseCase;
import com.electiva.speakup.application.port.in.ResponseQuestionUseCase;
import com.electiva.speakup.application.port.in.RetrieveQuestionsFromCategoryUseCase;
import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.CategoryRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.validation.AnswerValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionaryService implements AddQuestionToCategoryUseCase, ResponseQuestionUseCase, RetrieveQuestionsFromCategoryUseCase {

    private final AnswerRepositoryPort answerRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;
    private final CategoryRepositoryPort categoryRepositoryPort;


    @Override
    public void add(Long categoryId, Question question) {
        if (categoryId == null || question == null) {
            throw new IllegalArgumentException("Category id and question cannot be null");
        }

        var categoryFound = categoryRepositoryPort.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        QuestionType questionType = question.getType();

        if (QuestionType.FREE_TEXT.equals(questionType)) {
            if (!question.getOptions().isEmpty()) {
                throw new IllegalArgumentException("More than one option cannot be provided for free text question");
            }
        }

        if (QuestionType.SINGLE_CHOICE.equals(questionType)) {

            if (question.getOptions().stream().filter(Option::isCorrect).count() != 1) {
                throw new IllegalArgumentException("Single choice question should have exactly 1 correct option");
            }
        }


        var questionAdded = questionRepositoryPort.save(question);
        log.info("Question added: {}. Added to category {}", questionAdded.getId(), categoryFound.getName());
    }

    @Override
    public void respond(Long questionId, Answer answer) {
        Question question = questionRepositoryPort.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        List<Answer> existingAnswers = answerRepositoryPort.findAnswersByQuestionId(questionId);
        AnswerValidator.validate(question, answer, existingAnswers);

        answer.setQuestionId(question.getId());

        answerRepositoryPort.save(answer);
    }

    @Override
    public List<Question> retrieve(Long categoryId) {
        return questionRepositoryPort.findQuestionsByCategoryId(categoryId);
    }
}
