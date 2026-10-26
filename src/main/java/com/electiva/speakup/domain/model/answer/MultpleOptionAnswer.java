package com.electiva.speakup.domain.model.answer;

import lombok.*;

import java.util.List;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class MultpleOptionAnswer extends Answer{
    private List<Long> optionIds;

}
