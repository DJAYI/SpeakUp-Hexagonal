package com.electiva.speakup.infrastructure.adapter.out.persistence.memory.stubs;

import com.electiva.speakup.domain.model.Question;
import java.util.*;

public class QuestionMemoryStub {
    private final List<Question> questions = new ArrayList<>();

    public QuestionMemoryStub() {
    }

    public Question add(Question question) {
        question.setId((long) questions.size() + 1);

        // Asignar el id a las opciones de las preguntas, de forma incremental dentro de esa pregunta
        if (question.getOptions() != null) {
            int index = 1;
            for (var option : question.getOptions()) {
                option.setId(question.getId() * 1000 + index++);
            }
        }

        questions.add(question);

        return questions.getLast();
    }

    public void remove(Question question) {
        questions.remove(question);
    }

    public void update(Long id, Question updatedQuestion) {
        questions.stream()
                .filter(question -> question.getId().equals(id))
                .findFirst()
                .ifPresent(question -> {
                    question.setStatement(updatedQuestion.getStatement());
                    question.setType(updatedQuestion.getType());
                    question.setCategoryId(updatedQuestion.getCategoryId());
                    question.setOptions(new ArrayList<>(updatedQuestion.getOptions()));
                });
    }

    public void clear() {
        questions.clear();
    }

    public Optional<Question> findOne(Long id) {
        return questions.stream()
                .filter(q -> q.getId().equals(id))
                .findFirst();
    }

    public Collection<Question> readAll() {
        return Collections.unmodifiableCollection(questions);
    }
}