package com.electiva.speakup.infrastructure.adapter.in.http.mapper;

import com.electiva.speakup.domain.model.Option;
import com.electiva.speakup.domain.model.Question;
import com.electiva.speakup.domain.model.QuestionType;
import com.electiva.speakup.infrastructure.adapter.in.http.dto.QuestionResponse;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionResponseMapperTest {

    private final QuestionResponseMapper mapper = new QuestionResponseMapper();

    @Test
    void mapsQuestionWithPercentage() {
        Question question = Question.builder()
                .id(10L)
                .statement("¿Cuál es la respuesta?")
                .type(QuestionType.MULTIPLE_CHOICE)
                .categoryId(1L)
                .options(List.of(Option.builder().id(1001L).text("A").correct(true).build()))
                .build();

        QuestionResponse response = mapper.toResponse(question, 66.67);

        assertEquals(10L, response.id());
        assertEquals("¿Cuál es la respuesta?", response.statement());
        assertEquals(QuestionType.MULTIPLE_CHOICE, response.type());
        assertEquals(1L, response.categoryId());
        assertEquals(1, response.options().size());
        assertEquals(66.67, response.percentage());
    }

    @Test
    void usesZeroWhenQuestionHasNoVerification() {
        Question question = Question.builder()
                .id(10L)
                .type(QuestionType.FREE_TEXT)
                .build();

        List<QuestionResponse> responses = mapper.toResponse(List.of(question), Map.of());

        assertEquals(0.0, responses.get(0).percentage());
    }
}
