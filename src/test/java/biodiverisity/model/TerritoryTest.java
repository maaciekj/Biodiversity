package biodiverisity.model;

import biodiversity.model.organism.Organism;
import biodiversity.model.territory.*;
import biodiversity.view.ObserverFX;
import biodiversity.view.TerritoryObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        organism = Mockito.mock(Organism.class);
        Mockito.when(organism.getRow()).thenReturn(1);
        Mockito.when(organism.getCol()).thenReturn(1);
        territory.addInhabitant(organism);
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
        territory.doItsTurn();
        Mockito.verify(organism, Mockito.times(1)).doItsTurn();
    }
}
