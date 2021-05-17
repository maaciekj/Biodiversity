package biodiversity.controller;


import lombok.Getter;

@Getter
public class EvolutionaryLineDTO {

    private char speciesSign;
    private int adultPreferredBodyMass;
    private int maturityAge;
    private int maxAge;

    public EvolutionaryLineDTO() {
    }

    public EvolutionaryLineDTO(char speciesSign, int adultPreferredBodyMass, int maturityAge, int maxAge) {
        this.speciesSign = speciesSign;
        this.adultPreferredBodyMass = adultPreferredBodyMass;
        this.maturityAge = maturityAge;
        this.maxAge = maxAge;
    }

    public int getAdultPreferredBodyMass() {
        return adultPreferredBodyMass;
    }

    public void setAdultPreferredBodyMass(int adultPreferredBodyMass) {
        this.adultPreferredBodyMass = adultPreferredBodyMass;
    }

    public int getMaturityAge() {
        return maturityAge;
    }

    public void setMaturityAge(int maturityAge) {
        this.maturityAge = maturityAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "EvolutionaryLineDTO{" +
                "speciesSign=" + speciesSign +
                ", adultPreferredBodyMass=" + adultPreferredBodyMass +
                ", maturityAge=" + maturityAge +
                ", maxAge=" + maxAge +
                '}';
    }
}
