package com.electiva.speakup.domain.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Builder
public class Category {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String description;
}
