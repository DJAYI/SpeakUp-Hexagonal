package com.electiva.speakup.domain.model;

import lombok.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter @Setter

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Question {

    @EqualsAndHashCode.Include
    private Long id;

    private String statement;
    private QuestionType type;

    private Long categoryId;

    @Builder.Default
    private List<Option> options = new ArrayList<>();

}
