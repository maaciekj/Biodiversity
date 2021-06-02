package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public abstract class Behavior {

    protected Territory territory;
    protected NumberGenerator numberGenerator;

    public Behavior() {
    }

    public Behavior(Behavior behavior) {
        this.territory = behavior.getTerritory();
        this.numberGenerator = behavior.getNumberGenerator();
    }

    public Behavior(Territory territory, NumberGenerator numberGenerator) {
        this.territory = territory;
        this.numberGenerator = numberGenerator;
    }

    public abstract void doOutsourcedFunctions(Organism organism);

    public Territory getTerritory() {
        return territory;
    }

    public NumberGenerator getNumberGenerator() {
        return numberGenerator;
    }
}
