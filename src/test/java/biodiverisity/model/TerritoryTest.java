package biodiverisity.model;

import biodiversity.controller.*;
import biodiversity.model.territory.Territory;
import org.junit.jupiter.api.BeforeEach;

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
    }


}
