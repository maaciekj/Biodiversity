package biodiversity.model.organism.behavior.special_features;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.actions.Action;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.organism.behavior.BehaviorDecorator;

public class Revenging extends BehaviorDecorator {


    public Revenging(Behavior behavior) {
        super(behavior);
    }

    @Override
    public void reactOnAction(Action action, Organism acting, Organism reacting) {
        super.reactOnAction(action, acting, reacting);
        reactOnPreyAttempt();
        reactOnKillAttempt();
    }

    private void reactOnKillAttempt() {
    }

    private void reactOnPreyAttempt() {
    }


}
