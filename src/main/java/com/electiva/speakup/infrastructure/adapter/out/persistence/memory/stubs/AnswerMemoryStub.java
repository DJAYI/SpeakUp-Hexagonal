package com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs;

import com.electiva.speakup.domain.model.answer.Answer;
import java.util.*;

public class AnswerMemoryStub {
    private final List<Answer> answers = new ArrayList<>();

    public AnswerMemoryStub() {
    }

    public void add(Answer answer) {
        answer.setId((long) answers.size() + 1);
        answers.add(answer);
    }

    public void remove(Answer answer) {
        answers.remove(answer);
    }

    public void update(Long id, Answer updatedAnswer) {
        answers.stream()
                .filter(answer -> answer.getId().equals(id))
                .findFirst()
                .ifPresent(answer -> {
                    answer.setQuestionId(updatedAnswer.getQuestionId());
                });
    }

    public void clear() {
        answers.clear();
    }

    public Optional<Answer> findOne(Long id) {
        return answers.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    public Collection<Answer> readAll() {
        return Collections.unmodifiableCollection(answers);
    }
}