package biodiversity.model.organism;

import biodiversity.Constants;
import biodiversity.model.organism.behavior.Behavior;

public class Species {

    private final char sign;
    private final Behavior behavior;
    private final int adultPreferredBodyMass;
    private final int maturityAge;
    private final int maxAge;

    public Species(char sign, Behavior behavior, int adultPreferredBodyMass, int maxAge) {
        this.sign = sign;
        this.behavior = behavior;
        this.adultPreferredBodyMass = adultPreferredBodyMass;
        this.maturityAge = (int) Math.round(maxAge * Constants.MATURITY_AGE_TO_MAX_AGE_DEFAULT);
        this.maxAge = maxAge;
    }

    public void doOutsourcedFunctions(Organism organism) {
        behavior.doOutsourcedFunctions(organism);
    }

    public char getSign() {
        return sign;
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
