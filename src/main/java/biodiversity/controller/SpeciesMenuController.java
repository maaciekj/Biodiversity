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

    private void addProceedAction(){
        speciesMenu.addProceedButtonAction(event -> {
            SpeciesDTO speciesDTO = new SpeciesDTO.SpeciesDTOBuilder()
                    .build();
            speciesDTO.setSign(CharColorFX.findSignByNumber(territoryDTO.getOrdinalNumberOfSpeciesToBeCreated()));
            BehaviorDTO behaviorDTO = new BehaviorDTO();
            try {
                behaviorDTO.setFeedingStrategy(speciesMenu.getFeeding());
            } catch (InvalidUsersInputException e){
                setNewSpeciesMenu();
                return;
            }
            try{
                behaviorDTO.setReplicationStrategy(speciesMenu.getReplication());
            } catch (InvalidUsersInputException e){
                setNewSpeciesMenu();
                return;
            }
            speciesDTO.setBehaviorDTO(behaviorDTO);
            speciesDTO.setAdultPreferredBodyMass(speciesMenu.getMass());
            speciesDTO.setMaxAge(speciesMenu.getMaxAge());
            territoryDTO.addSpeciesDTO(speciesDTO);
            if (territoryDTO.getNumberOfSpecies()==territoryDTO.getSpeciesDTOs().size()){
                proceedToSimulation();
            } else {
                setNewSpeciesMenu();
            }
        });
    }

    private void setNewSpeciesMenu() {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO));
        speciesMenu.close();
    }

    private void proceedToSimulation(){
        logger.info("info from user complete. proceeding do build app");
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.startSimulation(territoryDTO);
        speciesMenu.close();
    }

}
