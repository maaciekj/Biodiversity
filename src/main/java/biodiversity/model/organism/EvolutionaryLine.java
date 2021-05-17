package biodiversity.model.organism;

public class EvolutionaryLine {

    private final Species species;
    private final String code;
    private final int adultPreferredBodyMass;
    private final int maturityAge;
    private final int maxAge;

    public EvolutionaryLine(Species species, int adultPreferredBodyMass, int maturityAge, int maxAge) {
        this.species = species;
        code = species.getSign() + ".0"; // this only for first ev line in species
        this.adultPreferredBodyMass = adultPreferredBodyMass;
        this.maturityAge = maturityAge;
        this.maxAge = maxAge;
    }

    // code: for first  species.getSign()+"."+"0"
    // TODO after mutation parent+"."+firstFreeNumberStartingFrom0

    public String getCode() {
        return code;
    }

    public int getAdultPreferredBodyMass() {
        return adultPreferredBodyMass;
    }

    public int getMaturityAge() {
        return maturityAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

}
