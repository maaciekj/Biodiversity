package biodiverisity.controller;

import biodiversity.controller.*;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryConfigTest {

    private TerritoryConfig territoryConfig;
    private final TerritoryDTO territoryDTO = new TerritoryDTO();
    private final ApplicationConfig applicationConfig = new ApplicationConfig();
    private final int height = 20;
    private final int width = 30;

    @BeforeEach
    public void beforeEach() {
        territoryConfig = new TerritoryConfig();
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
    public void territoryOfGivenDimensionsShouldBeCreated(){
        Territory territory = territoryConfig.createTerritoryWithAuxObjects(territoryDTO);
        assertEquals(territory.getHeight(), territoryDTO.getHeight());
        assertEquals(territory.getWidth(), territoryDTO.getWidth());
    }

    @Test
    public void organismOfGivenSingShouldAppearAtTerritory(){
        Territory territory = territoryConfig.createTerritoryWithAuxObjects(territoryDTO);
        assertTrue(territory.getNumberOfOrganismsOfSpecies(territoryDTO.getSpeciesDTOs().get(0).getSign())>=0);
    }

}
