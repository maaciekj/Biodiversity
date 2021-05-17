package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class BehaviorBasic extends Behavior {

    public BehaviorBasic(Territory territory, NumberGenerator numberGenerator) {
        super(territory, numberGenerator);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
    }

}
