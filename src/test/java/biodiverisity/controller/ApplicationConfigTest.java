package biodiverisity.controller;

import biodiversity.controller.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationConfigTest {

    private final TerritoryDTO territoryDTO = new TerritoryDTO();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();
    private final int height = 20;
    private final int width = 30;

    @BeforeEach
    public void beforeEach() {
        territoryDTO.setHeight(height);
        territoryDTO.setWidth(width);
        territoryDTO.setFertility(3);
        territoryDTO.setFertilityDiversity(5);
        BehaviorDTO behaviorDTO1 = BehaviorDTO.builder()
                .feedingStrategy("herbivore")
                .replicationStrategy("default")
                .build();
        SpeciesDTO speciesDTO1 = SpeciesDTO.builder()
                .sign('a')
                .behaviorDTO(behaviorDTO1)
                .adultPreferredBodyMass(20)
                .maxAge(30)
                .build();
        territoryDTO.addSpeciesDTO(speciesDTO1);
    }


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

    @Test
    public void territoryDTOWithNoInfoOnSpeciesShouldNotBeAccepted() {
        territoryDTO.getSpeciesDTOs().clear();
        assertThrows(InvalidDTOException.class, () -> applicationConfig.startSimulation(territoryDTO));
    }

    @Test
    public void territoryDTOWithFailedInfoOnSpeciesShouldNotBeAccepted() {
        BehaviorDTO behaviorDTO2 = BehaviorDTO.builder()
                .feedingStrategy("herbivore")
                .replicationStrategy("default")
                .build();
        SpeciesDTO speciesDTO2 = SpeciesDTO.builder()
                .sign('a')
                .behaviorDTO(behaviorDTO2)
                .adultPreferredBodyMass(-20)
                .maxAge(-30)
                .build();
        territoryDTO.addSpeciesDTO(speciesDTO2);
        assertThrows(InvalidDTOException.class, () -> applicationConfig.startSimulation(territoryDTO));
    }

}
