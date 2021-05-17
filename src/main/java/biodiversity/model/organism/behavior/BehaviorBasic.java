package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.actions.Action;
import biodiversity.model.special_events.SpecialEvent;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class BehaviorBasic extends Behavior {

    public BehaviorBasic(Territory territory, NumberGenerator numberGenerator) {
        super(territory, numberGenerator);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {    }

    @Override
    public void reactOnAction(Action action, Organism acting, Organism reacting) {
    }

    @Override
    public void reactOnSpecialEvent(SpecialEvent specialEvent, Organism reacting) {
    }
}
