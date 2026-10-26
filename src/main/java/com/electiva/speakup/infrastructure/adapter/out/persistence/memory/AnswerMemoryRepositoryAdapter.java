package com.electiva.speakup.infrastructure.adapter.out.persistence.memory;

import com.electiva.speakup.application.port.out.AnswerRepositoryPort;
import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs.AnswerMemoryStub;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswerMemoryRepositoryAdapter implements AnswerRepositoryPort {
    private final AnswerMemoryStub answerMemoryStub;

    public AnswerMemoryRepositoryAdapter() {
        this.answerMemoryStub = new AnswerMemoryStub();
    }

    @Override
    public void save(Answer answer) {
        answerMemoryStub.add(answer);
    }

    @Override
    public List<Answer> findAnswersByQuestionId(Long questionId) {
        return answerMemoryStub.readAll().stream()
                .filter(answer -> questionId.equals(answer.getQuestionId()))
                .toList();
    }
}
