package biodiversity.model.organism.behavior.special_features;

import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.organism.behavior.BehaviorDecorator;

public abstract class SpecialFeature extends BehaviorDecorator {
    public SpecialFeature(Behavior behavior) {
        super(behavior);
    }
}
