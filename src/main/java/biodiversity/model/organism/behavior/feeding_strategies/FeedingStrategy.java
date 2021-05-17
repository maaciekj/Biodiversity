package biodiversity.model.organism.behavior.feeding_strategies;

import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.organism.behavior.BehaviorDecorator;

public abstract class FeedingStrategy extends BehaviorDecorator {

    public FeedingStrategy(Behavior behavior) {
        super(behavior);
    }

}
