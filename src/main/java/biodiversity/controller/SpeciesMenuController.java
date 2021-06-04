package biodiversity.controller;

import biodiversity.view.CharColorFX;
import biodiversity.view.InvalidUsersInputException;
import biodiversity.view.Menu;
import biodiversity.view.SpeciesMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpeciesMenuController {

    private static final Logger logger = LogManager.getLogger(SpeciesMenuController.class);

    private final SpeciesMenu speciesMenu;
    private final TerritoryDTO territoryDTO;

    public SpeciesMenuController(SpeciesMenu speciesMenu) {
        this.speciesMenu = speciesMenu;
        this.territoryDTO = speciesMenu.getTerritoryDTO();
        addBackAction();
        addProceedAction();
    }

    private void addBackAction() {
        speciesMenu.addBackButtonAction(event -> {
            new MenuController(new Menu());
            speciesMenu.close();
        });
    }

    private void addProceedAction() {
        speciesMenu.addProceedButtonAction(event -> {
            String feedingStrategy;
            try {
                feedingStrategy = speciesMenu.getFeeding();
            } catch (InvalidUsersInputException e) {
                setNewSpeciesMenu(e.getMessage());
                return;
            }
            String replicationStrategy;
            try {
                replicationStrategy = speciesMenu.getReplication();
            } catch (InvalidUsersInputException e) {
                setNewSpeciesMenu(e.getMessage());
                return;
            }
            SpeciesDTO speciesDTO = createSpeciesDTO(feedingStrategy, replicationStrategy);
            territoryDTO.addSpeciesDTO(speciesDTO);
            if (territoryDTO.getNumberOfSpecies() == territoryDTO.getSpeciesDTOs().size()) {
                proceedToSimulation();
            } else {
                setNewSpeciesMenu();
            }
        });
    }

    private SpeciesDTO createSpeciesDTO(String feedingStrategy, String replicationStrategy) {
        BehaviorDTO behaviorDTO = new BehaviorDTO.BehaviorDTOBuilder()
                .feedingStrategy(feedingStrategy)
                .replicationStrategy(replicationStrategy)
                .build();
        SpeciesDTO speciesDTO = new SpeciesDTO.SpeciesDTOBuilder()
                .sign(CharColorFX.findSignByNumber(territoryDTO.getOrdinalNumberOfSpeciesToBeCreated()))
                .behaviorDTO(behaviorDTO)
                .adultPreferredBodyMass(speciesMenu.getMass())
                .maxAge(speciesMenu.getMaxAge())
                .build();
        return speciesDTO;
    }


    private void setNewSpeciesMenu(String specialInfo) {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO, specialInfo));
        speciesMenu.close();
    }


    private void setNewSpeciesMenu() {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO));
        speciesMenu.close();
    }

    private void proceedToSimulation() {
        logger.info("info from user complete. proceeding do build app");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.startSimulation(territoryDTO);
        speciesMenu.close();
    }

}
