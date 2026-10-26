package com.electiva.speakup.domain.model.answer;

import lombok.*;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class FreeTextAnswer extends Answer {
    private String text;

}
