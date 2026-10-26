package com.electiva.speakup.infrastructure.adapter.in.http.mapper;

import com.electiva.speakup.domain.model.answer.FreeTextAnswer;
import com.electiva.speakup.domain.model.answer.MultpleOptionAnswer;
import com.electiva.speakup.domain.model.answer.SingleOptionAnswer;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.AnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.FreeTextAnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.MultipleOptionAnswerResponse;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.answer.SingleOptionAnswerResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AnswerResponseMapperTest {

    private final AnswerResponseMapper mapper = new AnswerResponseMapper();

    @Test
    void mapsFreeTextAnswer() {
        FreeTextAnswer answer = FreeTextAnswer.builder()
                .id(1L)
                .questionId(10L)
                .text("Hola")
                .build();

        AnswerResponse response = mapper.toResponse(answer);

        FreeTextAnswerResponse freeTextResponse = assertInstanceOf(FreeTextAnswerResponse.class, response);
        assertEquals(1L, freeTextResponse.getId());
        assertEquals(10L, freeTextResponse.getQuestionId());
        assertEquals("Hola", freeTextResponse.getText());
    }

    @Test
    void mapsSingleOptionAnswer() {
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .id(2L)
                .questionId(10L)
                .optionId(3001L)
                .build();

        AnswerResponse response = mapper.toResponse(answer);

        SingleOptionAnswerResponse singleOptionResponse = assertInstanceOf(SingleOptionAnswerResponse.class, response);
        assertEquals(2L, singleOptionResponse.getId());
        assertEquals(10L, singleOptionResponse.getQuestionId());
        assertEquals(3001L, singleOptionResponse.getOptionId());
    }

    @Test
    void mapsMultipleOptionAnswer() {
        MultpleOptionAnswer answer = MultpleOptionAnswer.builder()
                .id(3L)
                .questionId(10L)
                .optionIds(List.of(3001L, 3002L))
                .build();

        AnswerResponse response = mapper.toResponse(answer);

        MultipleOptionAnswerResponse multipleOptionResponse = assertInstanceOf(MultipleOptionAnswerResponse.class, response);
        assertEquals(3L, multipleOptionResponse.getId());
        assertEquals(10L, multipleOptionResponse.getQuestionId());
        assertEquals(List.of(3001L, 3002L), multipleOptionResponse.getOptionIds());
    }

    @Test
    void mapsPercentage() {
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .id(2L)
                .questionId(10L)
                .optionId(3001L)
                .build();

        AnswerResponse response = mapper.toResponse(answer, 75.5);

        assertEquals(75.5, response.getPercentage());
    }

    @Test
    void mapsPercentagesByAnswerId() {
        SingleOptionAnswer answer = SingleOptionAnswer.builder()
                .id(2L)
                .questionId(10L)
                .optionId(3001L)
                .build();

        List<AnswerResponse> responses = mapper.toResponse(List.of(answer), Map.of(2L, 75.5));

        assertEquals(75.5, responses.get(0).getPercentage());
    }
}
