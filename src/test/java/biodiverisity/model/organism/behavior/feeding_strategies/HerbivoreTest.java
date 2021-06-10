package biodiverisity.model.organism.behavior.feeding_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.Species;
import biodiversity.model.organism.behavior.BehaviorBasic;
import biodiversity.model.organism.behavior.feeding_strategies.Herbivore;
import biodiversity.model.territory.NumberGeneratorRandom;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HerbivoreTest {


    private Territory territory;
    private Organism organism;
    private Species species;
    private Herbivore herbivore;

    @BeforeEach
    public void beforeEach(){
        territory = Mockito.mock(Territory.class);
        organism = Mockito.mock(Organism.class);
        BehaviorBasic behaviorBasic = new BehaviorBasic(territory, new NumberGeneratorRandom());
        herbivore = new Herbivore(behaviorBasic);
        species = Mockito.mock(Species.class);

    }

    @Test
    public void havingLotsOfStoredEnergyOrganismShouldNotTryToMove(){
        int energyConsumption = 5;
        int storedEnergy = energyConsumption*Constants.HERBIVORES_DEMAND_FACTOR+1;
        Mockito.when(organism.getEnergyConsumption()).thenReturn(energyConsumption);
        Mockito.when(organism.getStoredEnergy()).thenReturn(storedEnergy);
        herbivore.doOutsourcedFunctions(organism);
        Mockito.verify(territory, Mockito.times(0)).updateCoordinates(Mockito.any());
        Mockito.verify(territory, Mockito.times(0)).removeInhabitant(organism.getRow(), organism.getCol());
    }




}
