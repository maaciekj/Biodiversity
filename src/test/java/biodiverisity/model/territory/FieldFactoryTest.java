package biodiverisity.model.territory;

import biodiversity.controller.TerritoryDTO;
import biodiversity.model.territory.Field;
import biodiversity.model.territory.FieldFactory;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.NumberGeneratorRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FieldFactoryTest {


    //private TerritoryDTO territoryDTO = Mockito.mock(TerritoryDTO.class);
    private TerritoryDTO territoryDTO = new TerritoryDTO();
    private NumberGenerator numberGenerator = new NumberGeneratorRandom();
    private FieldFactory fieldFactory = new FieldFactory(numberGenerator);
    private int height = 20;
    private int width = 30;

    @BeforeEach
    public void beforeEach(){

        territoryDTO.setHeight(height);
        territoryDTO.setWidth(width);
        territoryDTO.setFertility(5);
        territoryDTO.setFertilityDiversity(0);
    }

    @Test
    public void fieldFactoryShouldProduceFieldArrayOfDimensionsGivenByTerritoryDTO(){
        /*Mockito.when(territoryDTO.getHeight()).thenReturn(height);
        Mockito.when(territoryDTO.getWidth()).thenReturn(width);
        Mockito.when(territoryDTO.getFertility()).thenReturn(4);
        Mockito.when(territoryDTO.getFertilityDiversity()).thenReturn(0);*/
        Field[][] fields = fieldFactory.createFieldPattern(territoryDTO);
        Assertions.assertEquals(fields.length, height);
        for (int i = 0; i < fields.length; i++) {
            Assertions.assertEquals(fields[i].length, width);
        }
    }

    @Test
    public void whenFertilityDiversityEqualsZeroAllFieldsShouldHaveSameFertility(){
        Field[][] fields = fieldFactory.createFieldPattern(territoryDTO);
        for (int row = 0; row < fields.length; row++) {
            for (int col = 0; col < fields[row].length; col++) {
                Assertions.assertEquals(fields[0][0].getGrowForTurn(), fields[row][col].getGrowForTurn());
            }
        }
    }
}