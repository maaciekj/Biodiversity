package biodiversity.model.organism.behavior.replication_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ManySmallChildrenAtEndOfLife extends ReplicationStrategy {

    public ManySmallChildrenAtEndOfLife(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected void replicate(Organism organism) {
        if (!checkInternalConditions(organism)) {
            return;
        }
        List<Field> freeFields = checkForFreeFields(organism);
        int numberOfChildren = calculateNumberOfChildren(organism);
        double freeFieldsNeededFactor = 0.75;
        if (freeFields.size() < numberOfChildren * freeFieldsNeededFactor) {
            return;
        }
        Collections.shuffle(freeFields);
        freeFields = freeFields.stream().limit(numberOfChildren).collect(Collectors.toList());
        for (Field freeField : freeFields) {
            createCreatureAndAssignToTerritory(freeField.getRow(), freeField.getCol(), organism);
        }
        dieFromExhaustion(organism);
    }

    @Override
    protected boolean checkInternalConditions(Organism organism) {
        if (organism.getAge() < organism.getMaturityAge()) {
            return false;
        }
        if (organism.getActiveBodyMass() < organism.getAdultPreferredBodyMass() * Constants.MIN_MASS_FACTOR_TO_REPRODUCE) {
            return false;
        }
        return numberGenerator.generateDouble() < Constants.BASIC_REPLICATION_PROBABILITY;
    }

    @Override
    protected List<Field> checkForFreeFields(Organism organism) {
        int rangeOfOffspringDispersal = 4;
        return territory.checkFreePlaces(organism.getRow(), organism.getCol(), rangeOfOffspringDispersal);
    }

    private int calculateNumberOfChildren(Organism organism) {
        return (int) (organism.getActiveBodyMass() / (calculateChildMass(organism)) * Constants.MANY_SMALL_CHILDREN_STRATEGY_EFFICIENCY);
    }

    @Override
    protected int calculateChildMass(Organism organism) {
        return (int) Math.round(organism.getAdultPreferredBodyMass() * Constants.CHILD_TO_ADULT_MASS_RATIO_MANY_SMALL_CHILDREN);
    }


    @Override
    protected void createCreatureAndAssignToTerritory(int row, int col, Organism organism) {
        Organism creatureChild = new Organism(organism.getSpecies(), organism.getEvolutionaryLine(),
            calculateChildMass(organism), calculateChildStoredEnergy(organism), organism.getTerritory(), organism.getNumberGenerator());
        creatureChild.setRow(row);
        creatureChild.setCol(col);
        territory.addInhabitant(creatureChild);
    }

    @Override
    protected int calculateChildStoredEnergy(Organism organism) {
        return organism.getStoredEnergy() / calculateNumberOfChildren(organism);
    }

    @Override
    protected void applyCostForParentOrganism(Organism organism) {
    }

    private void dieFromExhaustion(Organism organism) {
        territory.removeInhabitant(organism.getRow(), organism.getCol());
    }


}
