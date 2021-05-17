package biodiversity.controller;

public class SpeciesDTO {

    private char sign;
    private BehaviorDTO behaviorDTO;
    private EvolutionaryLineDTO evolutionaryLineDTO;

    public SpeciesDTO() {
    }

    public SpeciesDTO(char sign, BehaviorDTO behaviorDTO, EvolutionaryLineDTO evolutionaryLineDTO) {
        this.sign = sign;
        this.behaviorDTO = behaviorDTO;
        this.evolutionaryLineDTO = evolutionaryLineDTO;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public BehaviorDTO getBehaviorDTO() {
        return behaviorDTO;
    }

    public void setBehaviorDTO(BehaviorDTO behaviorDTO) {
        this.behaviorDTO = behaviorDTO;
    }

    public EvolutionaryLineDTO getEvolutionaryLineDTO() {
        return evolutionaryLineDTO;
    }

    public void setEvolutionaryLineDTO(EvolutionaryLineDTO evolutionaryLineDTO) {
        this.evolutionaryLineDTO = evolutionaryLineDTO;
    }

    @Override
    public String toString() {
        return "SpeciesDTO{" +
                "sign=" + sign +
                ", behaviorDTO=" + behaviorDTO +
                ", evolutionaryLineDTO=" + evolutionaryLineDTO +
                '}';
    }
}
