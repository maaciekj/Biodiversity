package biodiversity.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpeciesDTO {

    public SpeciesDTO() {
    }

    public SpeciesDTO(char sign, BehaviorDTO behaviorDTO, int adultPreferredBodyMass, int maxAge) {
        this.sign = sign;
        this.behaviorDTO = behaviorDTO;
        this.adultPreferredBodyMass = adultPreferredBodyMass;
        this.maxAge = maxAge;
    }

    private char sign;
    private BehaviorDTO behaviorDTO;
    private int adultPreferredBodyMass;
    private int maxAge;



}
