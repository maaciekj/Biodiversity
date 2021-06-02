package biodiversity.model.organism.behavior.feeding_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Herbivore extends FeedingStrategy {

    public Herbivore(Behavior behavior) {
        super(behavior);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
        super.doOutsourcedFunctions(organism);
        feedOnPlants(organism);
    }

    private void feedOnPlants(Organism organism) {
        organism.addEnergy(territory.feedOnPlants(organism.getRow(), organism.getCol(), calculateDemand(organism)));
    }

    private int calculateDemand(Organism organism) {
        return organism.getEnergyConsumption() * Constants.HERBIVORES_DEMAND_FACTOR - organism.getStoredEnergy();
    }

    @Override
    protected void migrate(Organism organism) {
        if (organism.getStoredEnergy() > Constants.HERBIVORES_DEMAND_FACTOR * organism.getEnergyConsumption()) {
            return;
        }
        List<Field> freeFields = territory.checkFreePlaces(organism.getRow(), organism.getCol(), 1);
        Collections.shuffle(freeFields);
        Field fieldToGo;
        try {
            fieldToGo = freeFields.stream().
                    max(Comparator.comparingInt(Field::getEdiblePlants)).orElseThrow(MaxNumberNotFound::new);
        } catch (MaxNumberNotFound e) {
            return;
        }
        moveTo(organism, fieldToGo.getRow(), fieldToGo.getCol());
    }
}
