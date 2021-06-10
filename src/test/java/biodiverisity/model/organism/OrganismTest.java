package biodiverisity.model.organism;

import biodiversity.model.organism.Organism;
import biodiversity.model.organism.Species;
import biodiversity.model.territory.NumberGeneratorRandom;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrganismTest {

    Organism organism;
    Territory territory;
    Species species;
    int defaultStoredEnergy = 20;

    @BeforeEach
    public void beforeEach(){
        int defaultBodyMass = 30;
        species = Mockito.mock(Species.class);
        Mockito.when(species.getSign()).thenReturn('a');
        Mockito.when(species.getMaxAge()).thenReturn(10);
        territory = Mockito.mock(Territory.class);
        organism = new Organism.Builder()
                .species(species)
                .numberGenerator(new NumberGeneratorRandom())
                .territory(territory)
                .activeBodyMass(defaultBodyMass)
                .storedEnergy(defaultStoredEnergy)
                .build();
    }

    @Test
    public void invokingDoItsTurnShouldInvokeDoOutsourcedFunctionsInSpecies(){
        organism.doItsTurn();
        Mockito.verify(species, Mockito.times(1)).doOutsourcedFunctions(organism);
    }

    @Test
    public void invokingDoItsTurnWithEmptySpeciesBehaviorShouldLowerStoredEnergy(){
        int energyBefore = organism.getStoredEnergy();
        organism.doItsTurn();
        int energyAfter = organism.getStoredEnergy();
        assertTrue(energyBefore>energyAfter);
    }

    @Test
    public void havingNegativeStoredEnergyShouldInvokeRemovingMethodInTerritory(){
        organism.consumeEnergy(defaultStoredEnergy+1);
        organism.doItsTurn();
        Mockito.verify(territory, Mockito.times(1)).removeInhabitant(organism.getRow(), organism.getCol());
    }

}
