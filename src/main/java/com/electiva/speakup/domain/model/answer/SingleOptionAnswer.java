package com.electiva.speakup.domain.model.answer;

import lombok.*;

@SuperBuilder
@Getter @Setter
@NoArgsConstructor
public class SingleOptionAnswer extends Answer{
    Long optionId;
}
