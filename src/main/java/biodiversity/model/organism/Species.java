package biodiversity.model.organism;

import biodiversity.Constants;
import biodiversity.model.organism.behavior.Behavior;

public class Species {

    private final char sign;
    private final Behavior behavior;
    private final int adultPreferredBodyMass;
    private final int maxAge;
    private final int maturityAge;


    public static class Builder {
        private char sign;
        private Behavior behavior;
        private int adultPreferredBodyMass;
        private int maxAge;

        public Builder sign(char sign) {
            this.sign = sign;
            return this;
        }

        public Builder behavior(Behavior behavior) {
            this.behavior = behavior;
            return this;
        }

        public Builder adultPreferredBodyMass(int adultPreferredBodyMass) {
            this.adultPreferredBodyMass = adultPreferredBodyMass;
            return this;
        }

        public Builder maxAge(int maxAge) {
            this.maxAge = maxAge;
            return this;
        }

        public Species build() {
            return new Species(this);
        }

    }


    private Species(Builder builder) {
        this.sign = builder.sign;
        this.behavior = builder.behavior;
        this.adultPreferredBodyMass = builder.adultPreferredBodyMass;
        this.maxAge = builder.maxAge;
        this.maturityAge = (int) Math.round(maxAge * Constants.MATURITY_AGE_TO_MAX_AGE_DEFAULT);
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
