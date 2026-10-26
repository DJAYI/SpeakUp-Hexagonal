package com.electiva.speakup.infrastructure.adapter.in.http.mapper;

import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import com.electiva.speakup.domain.model.answer.MultpleOptionAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.AnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.FreeTextAnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.MultipleOptionAnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.SingleOptionAnswerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AnswerResponseMapper {

    public AnswerResponse toResponse(Answer answer) {
        return toResponse(answer, 0.0);
    }

    public AnswerResponse toResponse(Answer answer, double percentage) {
        if (answer instanceof FreeTextAnswer freeText) {
            FreeTextAnswerResponse response = new FreeTextAnswerResponse();
            copyBase(freeText, response, percentage);
            response.setText(freeText.getText());
            return response;
        }

        if (answer instanceof SingleOptionAnswer single) {
            SingleOptionAnswerResponse response = new SingleOptionAnswerResponse();
            copyBase(single, response, percentage);
            response.setOptionId(single.getOptionId());
            return response;
        }

        if (answer instanceof MultpleOptionAnswer multiple) {
            MultipleOptionAnswerResponse response = new MultipleOptionAnswerResponse();
            copyBase(multiple, response, percentage);
            response.setOptionIds(multiple.getOptionIds());
            return response;
        }

        throw new IllegalArgumentException("Unsupported answer type: " + answer.getClass().getName());
    }

    public List<AnswerResponse> toResponse(List<Answer> answers) {
        return toResponse(answers, Map.of());
    }

    public List<AnswerResponse> toResponse(List<Answer> answers, Map<Long, Double> percentagesByAnswerId) {
        return answers.stream()
                .map(answer -> toResponse(
                        answer,
                        percentagesByAnswerId.getOrDefault(answer.getId(), 0.0)))
                .toList();
    }

    private void copyBase(Answer source, AnswerResponse target, double percentage) {
        target.setId(source.getId());
        target.setQuestionId(source.getQuestionId());
        target.setPercentage(percentage);
    }
}
