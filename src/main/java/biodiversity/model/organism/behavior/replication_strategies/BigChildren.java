package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.model.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;

public class BigChildren extends ReplicationStrategy{

    public BigChildren(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getActiveBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_BIG_CHILDREN);
    }

    @Override
    protected int calculateNeededEnergyForChildBodyMass(Organism organism) {
        return (int)Math.round(calculateChildMass(organism) * (Constants.ENERGY_NEED_FOR_BODY_MASS*0.5));
    }

    @Override
    protected void applyCostForParentOrganism(Organism organism) {
        organism.consumeEnergy(calculateNeededEnergyForChildBodyMass(organism) + calculateChildStoredEnergy(organism));
        organism.subtractBodyMass((int)Math.round(calculateChildMass(organism)*0.5));
    }
}
