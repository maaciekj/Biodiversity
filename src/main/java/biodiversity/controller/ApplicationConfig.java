package biodiversity.controller;

import biodiversity.Constants;
import biodiversity.DisplayConstants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.Species;
import biodiversity.model.organism.behavior.Behavior;
import biodiversity.model.organism.behavior.BehaviorBasic;
import biodiversity.model.organism.behavior.BehaviorDecorator;
import biodiversity.model.organism.behavior.feeding_strategies.Carnivore;
import biodiversity.model.organism.behavior.feeding_strategies.Herbivore;
import biodiversity.model.organism.behavior.replication_strategies.BigChildren;
import biodiversity.model.organism.behavior.replication_strategies.ManySmallChildrenAtEndOfLife;
import biodiversity.model.organism.behavior.replication_strategies.ReplicationStrategy;
import biodiversity.model.organism.behavior.replication_strategies.SmallChildren;
import biodiversity.model.territory.*;
import biodiversity.view.Menu;
import biodiversity.view.ObserverFX;
import biodiversity.view.SimulationDisplay;
import biodiversity.view.TerritoryObserver;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationConfig {

    private static final Logger logger = LogManager.getLogger(ApplicationConfig.class);

    public void startDefaultSimulation() {
        TerritoryDTO territoryDTO;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
            ApplicationConfig applicationConfig = new ApplicationConfig();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            territoryDTO = objectMapper.readValue(new File("src/main/resources/config.json"), TerritoryDTO.class);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            MenuController menuController = new MenuController(new Menu());
            return;
        }
        try{
            startSimulation(territoryDTO);
        } catch (InvalidDTOException e){
            logger.warn(e.getMessage());
            MenuController menuController = new MenuController(new Menu());
        }

    }

    public void startSimulation(TerritoryDTO territoryDTO) throws InvalidDTOException {
        /*if (!validateTerritoryDTO(territoryDTO)){
            throw new InvalidDTOException("invalid input data");
        }*/
        TerritoryObserver observer = new ObserverFX();
        NumberGenerator numberGenerator = new NumberGeneratorRandom();
        FieldFactory fieldFactory = new FieldFactory(numberGenerator);
        Counter counter = new Counter();
        Territory territory = new Territory.Builder()
                .emptyFieldSign(' ')
                .fields(fieldFactory.createFieldPattern(territoryDTO))
                .height(territoryDTO.getHeight())
                .width(territoryDTO.getWidth())
                .observer(observer)
                .counter(counter)
                .build();
        observer.addTerritory(territory);
        SimulationDisplay simulationDisplay = new SimulationDisplay(observer);
        List<Species> herbivores = getHerbivoreSpecies(territoryDTO, numberGenerator, territory);
        List<Species> carnivores = getCarnivoreSpecies(territoryDTO, numberGenerator, territory);
        List<Organism> herbivoreOrganisms = createOrganismsFromSpeciesList(numberGenerator, territory, herbivores);
        for (Organism herbivoreOrganism : herbivoreOrganisms) {
            territory.addInhabitant(herbivoreOrganism);
        }
        territory.setCarnivores(createOrganismsFromSpeciesList(numberGenerator, territory, carnivores));
        logger.info("simulation start");
    }

    private boolean validateTerritoryDTO(TerritoryDTO territoryDTO) {
    if (territoryDTO.getHeight()<=0){
        return false;
    }
    if (territoryDTO.getWidth()<=0){
        return false;
    }
    return true;
    }

    private List<Species> getHerbivoreSpecies(TerritoryDTO territoryDTO, NumberGenerator numberGenerator, Territory territory) {
        List<SpeciesDTO> speciesDTOsHerbivores = territoryDTO.getSpeciesDTOs().stream()
                .filter(speciesDTO -> speciesDTO.getBehaviorDTO().getFeedingStrategy().equals(DisplayConstants.HERBIVORE))
                .collect(Collectors.toList());
        return createListOfSpecies(speciesDTOsHerbivores, territory, numberGenerator);
    }

    private List<Species> getCarnivoreSpecies(TerritoryDTO territoryDTO, NumberGenerator numberGenerator, Territory territory) {
        List<SpeciesDTO> speciesDTOsCarnivores = territoryDTO.getSpeciesDTOs().stream()
                .filter(speciesDTO -> speciesDTO.getBehaviorDTO().getFeedingStrategy().equals(DisplayConstants.CARNIVORE))
                .collect(Collectors.toList());
        return createListOfSpecies(speciesDTOsCarnivores, territory, numberGenerator);
    }

    private List<Species> createListOfSpecies(List<SpeciesDTO> speciesDTOs, Territory territory, NumberGenerator numberGenerator) {
        List<Species> speciesList = new ArrayList<>();
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            Behavior behavior = new BehaviorBasic(territory, numberGenerator);
            BehaviorDecorator behaviorDecorator1 = addBehaviorDecoratorFeeding(speciesDTO, behavior);
            BehaviorDecorator behaviorDecorator2 = addBehaviorDecoratorReplication(speciesDTO, behaviorDecorator1);
            Species species = new Species.Builder()
                    .sign(speciesDTO.getSign())
                    .behavior(behaviorDecorator2)
                    .adultPreferredBodyMass(speciesDTO.getAdultPreferredBodyMass())
                    .maxAge(speciesDTO.getMaxAge())
                    .build();
            speciesList.add(species);
        }
        return speciesList;
    }

    private BehaviorDecorator addBehaviorDecoratorFeeding(SpeciesDTO speciesDTO, Behavior behavior) {
        BehaviorDecorator behaviorDecorator1;
        if (DisplayConstants.CARNIVORE.equals(speciesDTO.getBehaviorDTO().getFeedingStrategy())) {
            behaviorDecorator1 = new Carnivore(behavior);
        } else {
            behaviorDecorator1 = new Herbivore(behavior);
        }
        return behaviorDecorator1;
    }

    private BehaviorDecorator addBehaviorDecoratorReplication(SpeciesDTO speciesDTO, BehaviorDecorator behaviorDecorator1) {
        BehaviorDecorator behaviorDecorator2;
        switch (speciesDTO.getBehaviorDTO().getReplicationStrategy()) {
            case DisplayConstants.BIG_CHILDREN:
                behaviorDecorator2 = new BigChildren(behaviorDecorator1);
                break;
            case DisplayConstants.SMALL_CHILDREN:
                behaviorDecorator2 = new SmallChildren(behaviorDecorator1);
                break;
            case DisplayConstants.MANY_SMALL_AT_END:
                behaviorDecorator2 = new ManySmallChildrenAtEndOfLife(behaviorDecorator1);
                break;
            default:
                behaviorDecorator2 = new ReplicationStrategy(behaviorDecorator1);
        }
        return behaviorDecorator2;
    }

    private List<Organism> createOrganismsFromSpeciesList(NumberGenerator numberGenerator, Territory territory, List<Species> speciesList) {
        List<Organism> organisms = new ArrayList<>();
        for (Species species : speciesList) {
            organisms.addAll(buildOrganismsOfSpecies(species, territory, numberGenerator));
        }
        return organisms;
    }

    private List<Organism> buildOrganismsOfSpecies(Species species, Territory territory, NumberGenerator numberGenerator) {
        List<Organism> organisms = new ArrayList<>();
        int rowCenter = numberGenerator.generateRandomInt(territory.getHeight());
        int colCenter = numberGenerator.generateRandomInt(territory.getWidth());
        for (int i = 0; i < Constants.NUMBER_OF_ORGANISMS_OF_SPECIES_AT_THE_BEGINNING; i++) {
            Organism organism = new Organism.Builder()
                    .species(species)
                    .activeBodyMass(species.getAdultPreferredBodyMass())
                    .storedEnergy(species.getAdultPreferredBodyMass())
                    .territory(territory)
                    .numberGenerator(numberGenerator)
                    .build();
            do {
                int radius = 10;
                organism.setRow(rowCenter + numberGenerator.generateRandomInt(2 * radius) - radius);
                organism.setCol(colCenter + numberGenerator.generateRandomInt(2 * radius) - radius);
            } while (territory.placeIsOutOfTerritoryBoundaries(organism.getRow(), organism.getCol()));
            organisms.add(organism);
        }
        return organisms;
    }

}
