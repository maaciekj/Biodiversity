package biodiversity.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpeciesDTO {


    private char sign;
    private BehaviorDTO behaviorDTO;
    private int adultPreferredBodyMass;
    private int maxAge;



}
