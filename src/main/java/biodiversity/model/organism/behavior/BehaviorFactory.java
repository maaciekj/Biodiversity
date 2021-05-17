package biodiversity.model.organism.behavior;

import biodiversity.controller.BehaviorDTO;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class BehaviorFactory {

// here method creating decorated object
//
    public Behavior createBehavior(BehaviorDTO behaviorDTO, Territory territory, NumberGenerator numberGenerator){
        return new BehaviorBasic(territory, numberGenerator);
    }
}
