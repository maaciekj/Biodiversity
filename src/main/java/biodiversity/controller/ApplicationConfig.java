package biodiversity.controller;

import biodiversity.Constants;
import biodiversity.model.territory.Territory;
import biodiversity.view.CharColorFX;
import biodiversity.view.Menu;
import biodiversity.view.SimulationDisplay;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ApplicationConfig {

    private static final Logger logger = LogManager.getLogger(ApplicationConfig.class);

    public void startDefaultSimulation() {
        TerritoryDTO territoryDTO;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
            ApplicationConfig applicationConfig = new ApplicationConfig();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            territoryDTO = objectMapper.readValue(new File("src/main/resources/config.json"), TerritoryDTO.class);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            MenuController menuController = new MenuController(new Menu("unable to read file. please click configure"));
            return;
        }
        try {
            startSimulation(territoryDTO);
        } catch (InvalidDTOException e) {
            logger.warn(e.getMessage());
            MenuController menuController = new MenuController(new Menu(e.getMessage() + ". please click configure"));
        }
    }

    public void startSimulation(TerritoryDTO territoryDTO) throws InvalidDTOException {
        if (!validateTerritoryDTO(territoryDTO)) {
            throw new InvalidDTOException("invalid input data");
        }
        TerritoryConfig territoryConfig = new TerritoryConfig();
        Territory territory = territoryConfig.createTerritoryWithAuxObjects(territoryDTO);
        SimulationDisplay simulationDisplay = new SimulationDisplay(territory.getObserver());
        logger.info("simulation start");
    }

    private boolean validateTerritoryDTO(TerritoryDTO territoryDTO) {
        if (territoryDTO.getHeight() <= 0) {
            return false;
        }
        if (territoryDTO.getWidth() <= 0) {
            return false;
        }
        if (territoryDTO.getFertility() <= 0) {
            return false;
        }
        if (territoryDTO.getFertilityDiversity() < 0) {
            return false;
        }
        if (!validateSpeciesDTOs(territoryDTO)) {
            return false;
        }

        return true;
    }

    private boolean validateSpeciesDTOs(TerritoryDTO territoryDTO) {
        List<SpeciesDTO> speciesDTOs = territoryDTO.getSpeciesDTOs();
        if (speciesDTOs.isEmpty()) {
            return false;
        }
        if (speciesDTOs.size() > Constants.MAX_NUMBER_OF_SPECIES) {
            return false;
        }
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            if (CharColorFX.findColorByChar(speciesDTO.getSign()) == null) {
                return false;
            }
        }
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            if (speciesDTO.getBehaviorDTO() == null) {
                return false;
            }
        }
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            if (speciesDTO.getAdultPreferredBodyMass() <= 0) {
                return false;
            }
        }
        for (SpeciesDTO speciesDTO : speciesDTOs) {
            if (speciesDTO.getMaxAge() <= 0) {
                return false;
            }
        }
        return true;
    }

}
