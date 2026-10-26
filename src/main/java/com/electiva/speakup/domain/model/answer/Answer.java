package com.electiva.speakup.domain.model.answer;

import lombok.*;


@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class Answer {

    @EqualsAndHashCode.Include
    private Long id;

    private Long questionId;

}
