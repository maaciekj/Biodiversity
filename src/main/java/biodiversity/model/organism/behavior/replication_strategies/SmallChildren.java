package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.model.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.List;

public class SmallChildren extends ReplicationStrategy{

    public SmallChildren(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getActiveBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_SMALL_CHILDREN);
    }

    @Override
    protected List<Field> checkForFreeFields(Organism organism) {
        return territory.checkFreePlaces(organism.getRow(), organism.getCol(), 2);
    }

}
