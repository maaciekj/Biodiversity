package biodiverisity.model;

import biodiversity.controller.*;
import biodiversity.model.territory.Territory;
import biodiversity.view.ObserverFX;
import biodiversity.view.TerritoryObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TerritoryTest {

    private TerritoryConfig territoryConfig;
    private final TerritoryDTO territoryDTO = new TerritoryDTO();

    private final int height = 20;
    private final int width = 30;
    private Territory territory;


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
        territory = territoryConfig.createTerritoryWithAuxObjects(territoryDTO);
        TerritoryObserver observer = Mockito.mock(ObserverFX.class);
    }

    @Test
    public void trueShouldBeTrue(){
        assertTrue(true);
    }


}
