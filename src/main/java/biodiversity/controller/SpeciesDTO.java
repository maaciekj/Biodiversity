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
    private int maturityAge;
    private int maxAge;

    public SpeciesDTO() {
    }

    public SpeciesDTO(char sign, BehaviorDTO behaviorDTO, int adultPreferredBodyMass, int maturityAge, int maxAge) {
        this.sign = sign;
        this.behaviorDTO = behaviorDTO;
        this.adultPreferredBodyMass = adultPreferredBodyMass;
        this.maturityAge = maturityAge;
        this.maxAge = maxAge;
    }
}
