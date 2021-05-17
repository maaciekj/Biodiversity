package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.model.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.organism.behavior.BehaviorDecorator;
import biodiversity.model.territory.Field;

import java.util.Collections;
import java.util.List;

public class ReplicationStrategy extends BehaviorDecorator {


    public ReplicationStrategy(Behavior behavior) {
        super(behavior);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
        super.doOutsourcedFunctions(organism);
        replicate(organism);
    }

    protected void replicate(Organism organism) {
        if (!checkInternalConditions(organism)){
            return;
        }
        List<Field> freeFields = checkForFreeFields(organism);
        if (freeFields.size() == 0) {
            return;
        }
        Collections.shuffle(freeFields);
        Field chosenField = freeFields.get(numberGenerator.generateRandomInt(freeFields.size()));
        createCreatureAndAssignToTerritory(chosenField.getRow(), chosenField.getCol(), organism);
        applyCostForParentOrganism(organism);
    }

    protected boolean checkInternalConditions(Organism organism){
        if (organism.getAge() < organism.getMaturityAge()) {
            return false;
        }
        if (!organism.isHealthy()) {
            return false;
        }
        if (organism.getActiveBodyMass() < organism.getAdultPreferredBodyMass() * Constants.MIN_MASS_FACTOR_TO_REPRODUCE) {
            return false;
        }
        if (organism.getStoredEnergy() < calculateNeededEnergyForChildBodyMass(organism)) {
            return false;
        }
        if (numberGenerator.generateDouble()>Constants.BASIC_REPLICATION_PROBABILITY){
            return false;
        }
        return true;
    }


    protected List<Field> checkForFreeFields(Organism organism) {
        return territory.checkFreePlaces(organism.getRow(), organism.getCol(), 1);
    }

    protected int calculateNeededEnergyForChildBodyMass(Organism organism) {
        return calculateChildMass(organism) * Constants.ENERGY_NEED_FOR_BODY_MASS;
    }

    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getActiveBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_STANDARD);
    }

    protected int calculateChildStoredEnergy(Organism organism) {
        return calculateChildMass(organism);
    }

    protected void createCreatureAndAssignToTerritory(int row, int col, Organism organism) {
        Organism creatureChild = new Organism(organism.getSpecies(), organism.getEvolutionaryLine(), calculateChildMass(organism), calculateChildStoredEnergy(organism), organism.getTerritory(), organism.getNumberGenerator());
        // TODO here inf from maternal organism something due to clone(); something by setters
        creatureChild.setRow(row);
        creatureChild.setCol(col);
        territory.addInhabitant(creatureChild);
    }

    protected void applyCostForParentOrganism(Organism organism) {
        organism.consumeEnergy(calculateNeededEnergyForChildBodyMass(organism) + calculateChildStoredEnergy(organism));
    }

}
