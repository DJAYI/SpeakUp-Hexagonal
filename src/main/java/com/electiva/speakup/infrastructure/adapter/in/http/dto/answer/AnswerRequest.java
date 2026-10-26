package com.electiva.speakup.infrastructure.adapter.in.http.dto.answer;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FreeTextAnswerRequest.class, name = "FREE_TEXT"),
        @JsonSubTypes.Type(value = FreeTextAnswerRequest.class, name = "FreeTextAnswer"),
        @JsonSubTypes.Type(value = SingleOptionAnswerRequest.class, name = "SINGLE_CHOICE"),
        @JsonSubTypes.Type(value = SingleOptionAnswerRequest.class, name = "SingleOptionAnswer"),
        @JsonSubTypes.Type(value = MultipleOptionAnswerRequest.class, name = "MULTIPLE_CHOICE"),
        @JsonSubTypes.Type(value = MultipleOptionAnswerRequest.class, name = "MULTPLE_OPTION"),
        @JsonSubTypes.Type(value = MultipleOptionAnswerRequest.class, name = "MultpleOptionAnswer")
})
public abstract class AnswerRequest {
    // Base marker. No domain fields here.
}
