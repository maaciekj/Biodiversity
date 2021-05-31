package biodiverisity.model.territory;

import biodiversity.model.territory.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    public void beforeEach() {
        field = new Field(5, 0, 0);
    }


    @Test
    public void growPlantsShouldIncreaseEdiblePlantsOfGrowForTurnFactor() {
        int beforeGrowing = field.getEdiblePlants();
        field.growPlants();
        assertEquals(beforeGrowing + field.getGrowForTurn(), field.getEdiblePlants());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 10, 200, 0, -5})
    public void ediblePlantsShouldNotDropBelowZeroAfterFeeding(int number) {
        field.growPlants();
        field.feed(number);
        assertTrue(field.getEdiblePlants() >= 0);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 10, 200, 0, -5})
    public void ediblePlantsShouldIncreaseAfterFeeding(int number) {
        field.growPlants();
        int numberOfPlantsBefore = field.getEdiblePlants();
        field.feed(number);
        int numberOfPlantsAfter = field.getEdiblePlants();
        assertFalse(numberOfPlantsAfter > numberOfPlantsBefore);

    }


}
