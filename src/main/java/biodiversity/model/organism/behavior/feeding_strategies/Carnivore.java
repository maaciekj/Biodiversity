package biodiversity.model.organism.behavior.feeding_strategies;

import biodiversity.model.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.territory.Field;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Carnivore extends FeedingStrategy {

    public Carnivore(Behavior behavior) {
        super(behavior);
    }

    @Override
    public void doOutsourcedFunctions(Organism organism) {
        super.doOutsourcedFunctions(organism);
        preyOnAnimals(organism);
        migrate(organism);
    }

    protected void preyOnAnimals(Organism organism){
        if(organism.getStoredEnergy()> organism.getEnergyConsumption()*Constants.CARNIVORES_DEMAND_FACTOR){
            return;
        }
        List<Organism> toPreyOn = getListOfOrganismInNearby(organism);
        if (toPreyOn.size()==0){
            return;
        }
        Collections.shuffle(toPreyOn);
        // this option predator tries to eat all available prey
        // Streams have significant negative impact on performance due to creating huge number of new objects during simulation
        toPreyOn = toPreyOn.stream()
                    .filter(possiblePrey -> possiblePrey.getSign()!= organism.getSign())
                    .filter(possiblePrey -> possiblePrey.getActiveBodyMass()<organism.getActiveBodyMass())
                    .collect(Collectors.toList());
        for (Organism prey : toPreyOn) {
            if(numberGenerator.generateDouble()<Constants.CARNIVORES_BASIC_EFFICIENCY){
                organism.addEnergy((prey.getStoredEnergy()+prey.getActiveBodyMass())*Constants.ENERGY_EXTRACTED_FROM_BODY_MASS_BY_PREDATOR);
                territory.removeInhabitant(prey.getRow(), prey.getCol());
            }
        }
    }

    protected List<Organism> getListOfOrganismInNearby(Organism organism){
        return territory.checkOrganismsNearby(organism.getRow(), organism.getCol(), 1);
    }

    protected void migrate(Organism organism) {
        List<Field> freeFields = territory.checkFreePlaces(organism.getRow(), organism.getCol(), 2);
        if (freeFields.size()==0){
            return;
        }
        Collections.shuffle(freeFields);
        // Streams have significant impact on performance due to creating huge number of new objects during simulation
        int rangeOfSearching = 2;
        int numberOfPossiblePreyNeededToAttract = 3;
        List<Field> freeFieldsWithEnoughOrganismsNearby = freeFields.stream()
                .filter(field ->  territory.checkOrganismsNearby(field.getRow(), field.getCol(), rangeOfSearching)  // TODO try to remove second stream
                        .stream().filter(organism1 -> organism1.getSign() != organism.getSign()).count() >= numberOfPossiblePreyNeededToAttract)
                .collect(Collectors.toList());  // TODO findAny or findFirst
        if (freeFieldsWithEnoughOrganismsNearby.isEmpty()){
            Field fieldToGo = freeFields.get(numberGenerator.generateRandomInt(freeFields.size()));
            moveTo(organism, fieldToGo.getRow(), fieldToGo.getCol());
            return;
        }
        Field fieldToGo = freeFieldsWithEnoughOrganismsNearby.get(numberGenerator.generateRandomInt(freeFieldsWithEnoughOrganismsNearby.size()));
        moveTo(organism, fieldToGo.getRow(), fieldToGo.getCol());
    }


}
