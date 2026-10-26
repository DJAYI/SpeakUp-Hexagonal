package com.electiva.speakup.application.service;

import com.electiva.speakup.application.exception.QuestionNotFoundException;
import com.electiva.speakup.application.port.in.RetrieveAnswersFromQuestionUseCase;
import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.domain.model.answer.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnswerService implements RetrieveAnswersFromQuestionUseCase {

    private final AnswerRepositoryPort answerRepositoryPort;
    private final QuestionRepositoryPort questionRepositoryPort;

    @Override
    public List<Answer> retrieve(Long questionId) {
        questionRepositoryPort.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));

        List<Answer> answers = answerRepositoryPort.findAnswersByQuestionId(questionId);
        log.info("Retrieved {} answers for question {}", answers.size(), questionId);
        return answers;
    }
}
