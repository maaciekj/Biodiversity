package biodiverisity.model;

import biodiversity.model.organism.Organism;
import biodiversity.model.territory.*;
import biodiversity.view.ObserverFX;
import biodiversity.view.TerritoryObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {

    private Territory territory;
    private Organism organism;
    private Counter counter;

    @BeforeEach
    public void beforeEach() {
        TerritoryObserver observer = Mockito.mock(ObserverFX.class);
        counter = new Counter();
        NumberGenerator numberGeneratorRandom = new NumberGeneratorRandom();
        FieldFactory fieldFactory = new FieldFactory(numberGeneratorRandom);
        territory = new Territory.Builder()
                .emptyFieldSign(' ')
                .fields(fieldFactory.createFieldPattern(4, 4, 2, 0))
                .height(4)
                .width(4)
                .observer(observer)
                .counter(counter)
                .build();
    }


    @Test
    public void invokingDoItsTurnShouldIncreaseIterationNumber() {
        int iterationBeginning = territory.getIteration();
        territory.doItsTurn();
        int iterationEnd = territory.getIteration();
        assertEquals(iterationEnd, iterationBeginning + 1);
    }

    @Test
    public void invokingDoItsTurnInTerritoryShouldInvokeDoItsTurnInOrganism() {
        organism = mockOrganism('a', 1, 1);
        territory.addInhabitant(organism);
        territory.doItsTurn();
        Mockito.verify(organism, Mockito.times(1)).doItsTurn();
    }

    private Organism mockOrganism(char sign, int row, int col) {
        Organism organism1 = Mockito.mock(Organism.class);
        Mockito.when(organism1.getSign()).thenReturn(sign);
        Mockito.when(organism1.getRow()).thenReturn(row);
        Mockito.when(organism1.getCol()).thenReturn(col);
        return organism1;
    }

    @Test
    public void checkFreePlacesShouldReturnPlacesListOfExpectedSize(){
        organism = mockOrganism('a', 1, 1);
        territory.addInhabitant(organism);
        List<Field> freeFields = territory.checkFreePlaces(organism.getRow(), organism.getCol(), 1);
        assertEquals(freeFields.size(), 8);
    }

    @Test
    public void checkOrganismNearbyShouldNotIncludeOwnSpecies(){
        Organism organism1 = mockOrganism('a', 1, 1);
        Organism organism2 = mockOrganism('a', 0, 1);
        Organism organism3 = mockOrganism('b', 2, 2);
        territory.addInhabitant(organism1);
        territory.addInhabitant(organism2);
        territory.addInhabitant(organism3);
        List<Organism> organismsNearby = territory.checkOrganismsNearbyExcludingOwnSpecies(organism1.getRow(), organism1.getCol(), 3, organism1.getSign());
        assertEquals(1, organismsNearby.size());
    }



}
