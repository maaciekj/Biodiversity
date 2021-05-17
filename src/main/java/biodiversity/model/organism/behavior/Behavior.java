package biodiversity.model.organism.behavior;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.actions.Action;
import biodiversity.model.special_events.SpecialEvent;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public abstract class Behavior {

    // TODO Behavior consists only DoItsTurn or

    protected Territory territory;
    protected NumberGenerator numberGenerator;


    public Behavior() {
    }

    public Behavior(Behavior behavior){
        this.territory = behavior.getTerritory();
        this.numberGenerator = behavior.getNumberGenerator();
    }

    public Behavior(Territory territory, NumberGenerator numberGenerator) {
        this.territory = territory;
        this.numberGenerator = numberGenerator;
    }

    public abstract void doOutsourcedFunctions(Organism organism);


    public abstract void reactOnAction(Action action, Organism acting, Organism reacting);


    public abstract void reactOnSpecialEvent (SpecialEvent specialEvent, Organism reacting);

    public Territory getTerritory() {
        return territory;
    }

    public NumberGenerator getNumberGenerator() {
        return numberGenerator;
    }
}
