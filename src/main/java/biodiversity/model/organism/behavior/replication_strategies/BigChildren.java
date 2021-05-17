package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;

public class BigChildren extends ReplicationStrategy {

    public BigChildren(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getActiveBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_BIG_CHILDREN);
    }

    @Override
    protected int calculateNeededEnergyForChildBodyMass(Organism organism) {
        double costFactorForParentalOrganismEnergy = 0.5;
        return (int) Math.round(calculateChildMass(organism) * (Constants.ENERGY_NEED_FOR_BODY_MASS * costFactorForParentalOrganismEnergy));
    }

    @Override
    protected void applyCostForParentOrganism(Organism organism) {
        organism.consumeEnergy(calculateNeededEnergyForChildBodyMass(organism) + calculateChildStoredEnergy(organism));
        double costFactorForParentalOrganismBodyMass = 0.5;
        organism.subtractBodyMass((int) Math.round(calculateChildMass(organism) * costFactorForParentalOrganismBodyMass));
    }
}
