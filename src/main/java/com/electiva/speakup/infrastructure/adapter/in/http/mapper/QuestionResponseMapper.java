package com.electiva.speakup.infrastructure.adapter.in.http.mapper;

import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.QuestionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QuestionResponseMapper {

    public QuestionResponse toResponse(Question question, double percentage) {
        return new QuestionResponse(
                question.getId(),
                question.getStatement(),
                question.getType(),
                question.getCategoryId(),
                question.getOptions(),
                percentage
        );
    }

    public List<QuestionResponse> toResponse(List<Question> questions, Map<Long, Double> percentagesByQuestionId) {
        return questions.stream()
                .map(question -> toResponse(
                        question,
                        percentagesByQuestionId.getOrDefault(question.getId(), 0.0)))
                .toList();
    }
}
