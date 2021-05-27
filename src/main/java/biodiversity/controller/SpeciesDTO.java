package biodiversity.controller;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO {

    private char sign;
    private BehaviorDTO behaviorDTO;
    private int adultPreferredBodyMass;
    private int maxAge;

}
