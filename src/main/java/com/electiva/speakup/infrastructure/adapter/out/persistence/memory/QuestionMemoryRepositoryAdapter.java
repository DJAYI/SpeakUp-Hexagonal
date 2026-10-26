package com.electiva.speakup.infrastructure.adapter.out.persistence.memory;

import com.electiva.speakup.application.port.out.QuestionRepositoryPort;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs.QuestionMemoryStub;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionMemoryRepositoryAdapter implements QuestionRepositoryPort {
    private final QuestionMemoryStub questionMemoryStub;

    public QuestionMemoryRepositoryAdapter() {
        this.questionMemoryStub = new QuestionMemoryStub();
    }

    @Override
    public Question save(Question question) {
        return questionMemoryStub.add(question);
    }

    @Override
    public List<Question> findQuestionsByCategoryId(Long categoryId) {
        return questionMemoryStub.readAll().stream()
                .filter(question -> question.getCategoryId().equals(categoryId))
                .toList();
    }

    @Override
    public Optional<Question> findById(Long questionId) {
        return questionMemoryStub.findOne(questionId);
    }
}
