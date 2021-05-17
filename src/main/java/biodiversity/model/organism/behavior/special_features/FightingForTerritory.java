package biodiversity.model.organism.behavior.special_features;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;

public class FightingForTerritory extends SpecialFeature {

    public FightingForTerritory(Behavior behavior) {
        super(behavior);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
        super.doOutsourcedFunctions(organism);
        attackOtherAnimal();
    }

    private void attackOtherAnimal(){

    }

}
