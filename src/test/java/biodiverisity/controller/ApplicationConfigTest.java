package biodiverisity.controller;

import biodiversity.controller.ApplicationConfig;
import biodiversity.controller.InvalidDTOException;
import biodiversity.controller.TerritoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationConfigTest {

    private final TerritoryDTO territoryDTO = new TerritoryDTO();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();
    private final int height = 20;
    private final int width = 30;

    @Test
    public void territoryDTOWithNegativeHeightShouldNotBeAccepted() {
        territoryDTO.setHeight(-10);
        assertThrows(InvalidDTOException.class, () -> applicationConfig.startSimulation(territoryDTO));
    }

    @Test
    public void territoryDTOWithNegativeWidthShouldNotBeAccepted() {
        territoryDTO.setWidth(-5);
        assertThrows(InvalidDTOException.class, () -> applicationConfig.startSimulation(territoryDTO));
    }

}
