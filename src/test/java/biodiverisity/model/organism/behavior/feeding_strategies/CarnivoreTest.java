package biodiverisity.model.organism.behavior.feeding_strategies;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.model.organism.behavior.BehaviorBasic;
import biodiversity.model.organism.behavior.feeding_strategies.Carnivore;
import biodiversity.model.territory.Field;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.NumberGeneratorRandom;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarnivoreTest {

    private Territory territory;
    private Organism organism;
    private Carnivore carnivore;
    private Organism prey;
    private NumberGenerator numberGenerator;

    @BeforeEach
    public void beforeEach() {
        territory = mock(Territory.class);
        organism = mock(Organism.class);
        numberGenerator = mock(NumberGeneratorRandom.class);
        when(numberGenerator.generateDouble()).thenReturn(Constants.CARNIVORES_BASIC_EFFICIENCY - 0.05);
        BehaviorBasic behaviorBasic = new BehaviorBasic(territory, numberGenerator);
        carnivore = new Carnivore(behaviorBasic);
        prey = mock(Organism.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 100})
    public void carnivoreShouldNotEatOrganismOfBiggerMass(int mass) {
        when(organism.getActiveBodyMass()).thenReturn(mass);
        when(organism.getSign()).thenReturn('a');
        when(prey.getActiveBodyMass()).thenReturn(mass + 1);
        when(prey.getSign()).thenReturn('b');
        List<Organism> preyList = List.of(prey);
        when(territory.checkOrganismsNearbyExcludingOwnSpecies(anyInt(), anyInt(), anyInt(), anyChar())).thenReturn(preyList);
        carnivore.doOutsourcedFunctions(organism);
        Mockito.verify(organism, Mockito.times(0)).addEnergy(anyInt());
    }

    @Test
    public void havingNotEnoughPreyNearbyOrganismShouldMigrateToAnyFreeField() {
        Field field1 = mock(Field.class);
        List<Field> freeFields = List.of(field1);
        when(territory.checkFreePlaces(anyInt(), anyInt(), anyInt())).thenReturn(freeFields);
        when(territory.checkOrganismsNearbyExcludingOwnSpecies(anyInt(), anyInt(), anyInt(), anyChar())).thenReturn(new ArrayList<>());
        carnivore.doOutsourcedFunctions(organism);
        Mockito.verify(territory, Mockito.times(1)).updateCoordinates(organism);
    }

}
