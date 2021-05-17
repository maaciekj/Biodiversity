package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.List;

public class SmallChildren extends ReplicationStrategy {

    public SmallChildren(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getActiveBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_SMALL_CHILDREN);
    }

    @Override
    protected List<Field> checkForFreeFields(Organism organism) {
        int rangeOfOffSpringDispersal = 2;
        return territory.checkFreePlaces(organism.getRow(), organism.getCol(), rangeOfOffSpringDispersal);
    }

}
