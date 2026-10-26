package com.electiva.speakup.infrastructure.adapter.in.http.mapper;

import com.electiva.speakup.domain.model.answer.Answer;
import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import com.electiva.speakup.domain.model.answer.MultpleOptionAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.AnswerRequest;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.FreeTextAnswerRequest;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.MultipleOptionAnswerRequest;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.SingleOptionAnswerRequest;
import org.springframework.stereotype.Component;

@Component
public class AnswerRequestMapper {

    public Answer toDomain(AnswerRequest request) {
        if (request instanceof FreeTextAnswerRequest freeText) {
            return FreeTextAnswer.builder()
                    .text(freeText.getText())
                    .build();
        }

        if (request instanceof SingleOptionAnswerRequest single) {
            SingleOptionAnswer answer = SingleOptionAnswer.builder().build();
            answer.setOptionId(single.getOptionId());
            return answer;
        }

        if (request instanceof MultipleOptionAnswerRequest multiple) {
            return MultpleOptionAnswer.builder()
                    .optionIds(multiple.getOptionIds())
                    .build();
        }

        throw new IllegalArgumentException("Unsupported answer request type: " + request.getClass().getName());
    }
}
