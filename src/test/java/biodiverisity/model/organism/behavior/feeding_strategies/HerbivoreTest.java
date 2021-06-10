package biodiverisity.model.organism.behavior.feeding_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.Species;
import biodiversity.model.organism.behavior.BehaviorBasic;
import biodiversity.model.organism.behavior.feeding_strategies.Herbivore;
import biodiversity.model.territory.Field;
import biodiversity.model.territory.NumberGeneratorRandom;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HerbivoreTest {

    private Territory territory;
    private Organism organism;
    private Species species;
    private Herbivore herbivore;

    @BeforeEach
    public void beforeEach(){
        territory = mock(Territory.class);
        organism = mock(Organism.class);
        BehaviorBasic behaviorBasic = new BehaviorBasic(territory, new NumberGeneratorRandom());
        herbivore = new Herbivore(behaviorBasic);
        species = mock(Species.class);
    }

    @Test
    public void havingLotsOfStoredEnergyOrganismShouldNotTryToMove(){
        int energyConsumption = 5;
        int storedEnergy = energyConsumption*Constants.HERBIVORES_DEMAND_FACTOR+1;
        when(organism.getEnergyConsumption()).thenReturn(energyConsumption);
        when(organism.getStoredEnergy()).thenReturn(storedEnergy);
        herbivore.doOutsourcedFunctions(organism);
        verify(territory, Mockito.times(0)).updateCoordinates(Mockito.any());
        verify(territory, Mockito.times(0)).removeInhabitant(organism.getRow(), organism.getCol());
    }

    @Test
    public void fieldWithGreaterEdiblePlantsNumberShouldBeChosenToGo(){
        int energyConsumption = 5;
        int storedEnergy = energyConsumption*Constants.HERBIVORES_DEMAND_FACTOR-1;
        when(organism.getEnergyConsumption()).thenReturn(energyConsumption);
        when(organism.getStoredEnergy()).thenReturn(storedEnergy);
        int baseEdiblePlants = 5;
        Field field1 = mock(Field.class);
        when(field1.getEdiblePlants()).thenReturn(baseEdiblePlants);
        when(field1.getRow()).thenReturn(1);
        when(field1.getCol()).thenReturn(1);
        Field field2 = mock(Field.class);
        when(field2.getEdiblePlants()).thenReturn(baseEdiblePlants+1);
        when(field2.getRow()).thenReturn(2);
        when(field2.getCol()).thenReturn(2);
        List<Field> fieldMocks = List.of(field1, field2);
        ArgumentCaptor<Integer> valueCapture = ArgumentCaptor.forClass(Integer.class);
        when(territory.checkFreePlaces(anyInt(), anyInt(), anyInt())).thenReturn(fieldMocks);
        when(territory.getField(anyInt(), anyInt())).thenReturn(mock(Field.class));
        doNothing().when(organism).setRow(valueCapture.capture());
        herbivore.doOutsourcedFunctions(organism);
        System.out.println("row of selected field: " + valueCapture.getValue());
        assertEquals(field2.getRow(), valueCapture.getValue());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 8, 10})
    public void invokingDoItsTurnShouldCreateDemand(int number){

    }

    @Test
    public void invokingDoItsTurnShouldIncreaseStoredEnengyInOrganismWithNumberGivenByTerritory(){

    }


}
