package biodiversity.controller;

import biodiversity.Constants;
import biodiversity.model.organism.EvolutionaryLine;
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
import biodiversity.DisplayConstants;
import biodiversity.view.ObserverFX;
import biodiversity.view.SimulationDisplay;
import biodiversity.view.TerritoryObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationConfig {

    public void startDefaultSimulation(){
        TerritoryDTO territoryDTO = buildDefaultDTO();
        startSimulation(territoryDTO);
    }

    private TerritoryDTO buildDefaultDTO() {
        TerritoryDTO territoryDTO = new TerritoryDTO();
        territoryDTO.setFertility(7);
        territoryDTO.setFertilityDiversity(1);
        territoryDTO.setNumberOfSpecies(4);

        BehaviorDTO behaviorDTO1 = new BehaviorDTO("herbivore", "default");
        EvolutionaryLineDTO evolutionaryLineDTO1 = new EvolutionaryLineDTO('a', 60, 20, 100);
        SpeciesDTO speciesDTO1 = new SpeciesDTO('a', behaviorDTO1, evolutionaryLineDTO1);
        territoryDTO.addSpeciesDTO(speciesDTO1);

        BehaviorDTO behaviorDTO2 = new BehaviorDTO("carnivore", "big children");
        EvolutionaryLineDTO evolutionaryLineDTO2 = new EvolutionaryLineDTO('b', 80, 80, 400);
        SpeciesDTO speciesDTO2 = new SpeciesDTO('b', behaviorDTO2, evolutionaryLineDTO2);
        territoryDTO.addSpeciesDTO(speciesDTO2);

        BehaviorDTO behaviorDTO3 = new BehaviorDTO("herbivore", "small children");
        EvolutionaryLineDTO evolutionaryLineDTO3 = new EvolutionaryLineDTO('c', 80, 40, 200);
        SpeciesDTO speciesDTO3 = new SpeciesDTO('c', behaviorDTO3, evolutionaryLineDTO3);
        territoryDTO.addSpeciesDTO(speciesDTO3);

        BehaviorDTO behaviorDTO4 = new BehaviorDTO("herbivore", "many small children at end of life");
        EvolutionaryLineDTO evolutionaryLineDTO4 = new EvolutionaryLineDTO('d', 70, 80, 400);
        SpeciesDTO speciesDTO4 = new SpeciesDTO('d', behaviorDTO4, evolutionaryLineDTO4);
        territoryDTO.addSpeciesDTO(speciesDTO4);

        return territoryDTO;
    }

    public void startSimulation(TerritoryDTO territoryDTO){
        int height = territoryDTO.getHeight();
        int width = territoryDTO.getWidth();
        TerritoryObserver observer = new ObserverFX();
        NumberGenerator numberGenerator = new NumberGeneratorRandom();
        FieldFactory fieldFactory = new FieldFactory(numberGenerator);
        Counter counter = new Counter();
        Territory territory = new Territory(' ', height, width, fieldFactory.createFieldPattern(height, width, territoryDTO.getFertility(), territoryDTO.getFertilityDiversity()), observer, counter);
        observer.addTerritory(territory);
        SimulationDisplay simulationDisplay = new SimulationDisplay(observer);
        List<SpeciesDTO> speciesDTOsHerbivores = territoryDTO.getSpeciesDTOs().stream().filter(speciesDTO -> speciesDTO.getBehaviorDTO().getFeedingStrategy().equals(DisplayConstants.HERBIVORE)).collect(Collectors.toList());
        List<SpeciesDTO> speciesDTOsCarnivores = territoryDTO.getSpeciesDTOs().stream().filter(speciesDTO -> speciesDTO.getBehaviorDTO().getFeedingStrategy().equals(DisplayConstants.CARNIVORE)).collect(Collectors.toList());
        List<Species> carnivores = createListOfSpecies(speciesDTOsCarnivores, territory, numberGenerator);
        List<Species> herbivores = createListOfSpecies(speciesDTOsHerbivores, territory, numberGenerator);
        List<Organism> herbivoreOrganisms = new ArrayList<>();
        for (Species species : herbivores) {
            herbivoreOrganisms.addAll(buildOrganismsOfSpecies(species, species.getEvolutionaryLines().get(0), territory, numberGenerator));
        }
        for (Organism otherOrganism : herbivoreOrganisms) {
            territory.addInhabitant(otherOrganism);
        }
        List<Organism> carnivoreOrganisms = new ArrayList<>();
        for (Species carnivore : carnivores) {
            carnivoreOrganisms.addAll(buildOrganismsOfSpecies(carnivore, carnivore.getEvolutionaryLines().get(0), territory, numberGenerator));
        }
        territory.setCarnivores(carnivoreOrganisms);
    }

    private List<Species> createListOfSpecies(List<SpeciesDTO> speciesDTOs, Territory territory, NumberGenerator numberGenerator){
        List<Species> speciesList = new ArrayList<>();
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            Behavior behavior = new BehaviorBasic(territory, numberGenerator);
            BehaviorDecorator behaviorDecorator1;
            if (DisplayConstants.CARNIVORE.equals(speciesDTO.getBehaviorDTO().getFeedingStrategy())) {
                behaviorDecorator1 = new Carnivore(behavior);
            } else {
                behaviorDecorator1 = new Herbivore(behavior);
            }
            BehaviorDecorator behaviorDecorator2;
            switch (speciesDTO.getBehaviorDTO().getReplicationStrategy()){
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
            Species species = new Species(speciesDTO.getSign(), behaviorDecorator2);
            EvolutionaryLineDTO evolutionaryLineDTO = speciesDTO.getEvolutionaryLineDTO();
            EvolutionaryLine evolutionaryLine = new EvolutionaryLine(species, evolutionaryLineDTO.getAdultPreferredBodyMass(), evolutionaryLineDTO.getMaturityAge(), evolutionaryLineDTO.getMaxAge());
            species.addEvolutionaryLine(evolutionaryLine);
            speciesList.add(species);
        }
        return speciesList;
    }

    private List<Organism> buildOrganismsOfSpecies(Species species, EvolutionaryLine evolutionaryLine, Territory territory, NumberGenerator numberGenerator){
        List<Organism> organisms = new ArrayList<>();
        int rowCenter = numberGenerator.generateRandomInt(territory.getHeight());
        int colCenter = numberGenerator.generateRandomInt(territory.getWidth());
        for (int i = 0; i < Constants.NUMBER_OF_ORGANISMS_OF_SPECIES_AT_THE_BEGINNING; i++) {
            Organism organism = new Organism(species, evolutionaryLine, evolutionaryLine.getAdultPreferredBodyMass(), evolutionaryLine.getAdultPreferredBodyMass(), territory, numberGenerator);
            do{
                organism.setRow(rowCenter+numberGenerator.generateRandomInt(20)-10);
                organism.setCol(colCenter+numberGenerator.generateRandomInt(20)-10);
            } while (territory.placeIsOutOfTerritoryBoundaries(organism.getRow(), organism.getCol()));
            organisms.add(organism);
        }
        return organisms;
    }

}
