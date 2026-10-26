package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FreeTextAnswerResponse.class, name = "FREE_TEXT"),
        @JsonSubTypes.Type(value = SingleOptionAnswerResponse.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = MultipleOptionAnswerResponse.class, name = "MULTIPLE_CHOICE")
})
@Getter
@Setter
public abstract class AnswerResponse {
    private Long id;
    private Long questionId;
    private double percentage;
}
