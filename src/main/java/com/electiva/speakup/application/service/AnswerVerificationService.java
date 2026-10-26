package com.electiva.speakup.application.service;

import com.electiva.speakup.application.exception.QuestionNotFoundException;
import com.electiva.speakup.application.port.in.VerifyAnswersUseCase;
import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.application.result.AnswerVerification;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.AnswerScorer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerVerificationService implements VerifyAnswersUseCase {

    private final QuestionRepositoryPort questionRepositoryPort;
    private final AnswerRepositoryPort answerRepositoryPort;

    @Override
    public List<AnswerVerification> verifyByCategory(Long categoryId) {
        List<Question> questions = questionRepositoryPort.findQuestionsByCategoryId(categoryId);
        List<AnswerVerification> verifications = questions.stream()
                .flatMap(question -> verifyQuestionAnswers(question).stream())
                .toList();

        log.info("Verified {} answers for category {}", verifications.size(), categoryId);
        return verifications;
    }

    @Override
    public List<AnswerVerification> verifyByQuestion(Long questionId) {
        Question question = questionRepositoryPort.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        List<AnswerVerification> verifications = verifyQuestionAnswers(question);
        log.info("Verified {} answers for question {}", verifications.size(), questionId);
        return verifications;
    }

    private List<AnswerVerification> verifyQuestionAnswers(Question question) {
        List<Answer> answers = answerRepositoryPort.findAnswersByQuestionId(question.getId());

        return answers.stream()
                .map(answer -> new AnswerVerification(
                        question.getId(),
                        answer.getId(),
                        AnswerScorer.percentage(question, answer)))
                .toList();
    }
}
