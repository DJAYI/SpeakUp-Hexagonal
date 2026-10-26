package com.electiva.speakup.domain.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder

@Getter @Setter

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Option {
    @EqualsAndHashCode.Include
    private Long id;

    private String text;

    private boolean correct;

}
