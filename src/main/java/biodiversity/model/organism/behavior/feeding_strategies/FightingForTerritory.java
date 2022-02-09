package biodiversity.model.organism.behavior.feeding_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FightingForTerritory extends Herbivore{

    public FightingForTerritory(Behavior behavior) {
        super(behavior);
    }

    @Override
    protected void migrate(Organism organism) {

        if (organism.getStoredEnergy() > Constants.HERBIVORES_DEMAND_FACTOR * organism.getEnergyConsumption()) {
            return;
        }
        List<Field> neighbouringPlaces = territory.getNeighbouringPlaces(organism.getRow(), organism.getCol(), 1);
        if (neighbouringPlaces.isEmpty()) {
            return;
        }
        neighbouringPlaces = neighbouringPlaces.stream().sorted(Comparator.comparingInt(Field::getEdiblePlants)).collect(Collectors.toList());
        Collections.reverse(neighbouringPlaces);
        Field fieldToGo = neighbouringPlaces.get(0);
        if (fieldToGo.getEdiblePlants() < territory.getField(organism.getRow(), organism.getCol()).getEdiblePlants()) {
            return;
        }
        Organism inhabitant = territory.getInhabitant(fieldToGo.getRow(), fieldToGo.getCol());

        if (inhabitant==null){
            moveTo(organism, fieldToGo.getRow(), fieldToGo.getCol());
            return;
        }
        if (inhabitant.getActiveBodyMass() > organism.getActiveBodyMass()|| inhabitant.getSign()== organism.getSign()) {
                return;
        }
        territory.removeInhabitant(fieldToGo.getRow(), fieldToGo.getCol());
        moveTo(organism, fieldToGo.getRow(), fieldToGo.getCol());
    }
}
