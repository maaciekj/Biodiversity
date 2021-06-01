package biodiverisity.controller;

import biodiversity.controller.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationConfigTest {

    private final TerritoryDTO territoryDTO = new TerritoryDTO();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();
    private final int height = 20;
    private final int width = 30;



    @Test
    public void territoryDTOWithNegativeHeightShouldNotBeAccepted(){
        territoryDTO.setHeight(-10);
        assertThrows(InvalidDTOException.class, () -> applicationConfig.startSimulation(territoryDTO));
    }

    @Test
    public void territoryDTOWithNegativeWidthShouldNotBeAccepted(){
        territoryDTO.setWidth(-5);
        assertThrows(InvalidDTOException.class, ()-> applicationConfig.startSimulation(territoryDTO));
    }




}
