package biodiversity.controller;

import biodiversity.Constants;
import biodiversity.view.InvalidUsersInputException;
import biodiversity.view.Menu;
import biodiversity.view.SpeciesMenu;
import biodiversity.view.TerritoryMenu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerritoryMenuController {

    private static final Logger logger = LogManager.getLogger(TerritoryMenuController.class);

    private final TerritoryMenu territoryMenu;

    public TerritoryMenuController(TerritoryMenu territoryMenu) {
        this.territoryMenu = territoryMenu;
        addBackAction();
        addProceedAction();
    }

    private void addBackAction() {
        territoryMenu.addBackButtonAction(event -> {
            new MenuController(new Menu());
            territoryMenu.close();
        });
    }

    private void addProceedAction() {
        territoryMenu.addProceedButtonAction(event -> {
            TerritoryDTO territoryDTO = new TerritoryDTO();
            try {
                territoryDTO.setNumberOfSpecies(collectAndValidateNumberOfSpecies());
            } catch (InvalidUsersInputException e) {
                logger.warn(e.getMessage());
                setNewTerritoryMenuAndClosePresent(e.getMessage());
                return;
            }
            try {
                territoryDTO.setFertility(collectAndValidateFertility());
            } catch (InvalidUsersInputException e) {
                logger.warn(e.getMessage());
                setNewTerritoryMenuAndClosePresent(e.getMessage());
                return;
            }
            try {
                String diversity = territoryMenu.getDiversity();
                territoryDTO.setFertilityDiversity(DiversityTextInt.valueOf(diversity.toUpperCase()).getDiversityCode());
            } catch (InvalidUsersInputException e) {
                logger.warn(e.getMessage());
                setNewTerritoryMenuAndClosePresent(e.getMessage());
                return;
            }
            goToSpeciesMenu(territoryDTO);
        });
    }

    private int collectAndValidateNumberOfSpecies() throws InvalidUsersInputException {
       int numberOfSpecies = 0;
        try {
            numberOfSpecies = Integer.parseInt(territoryMenu.getHowManySpecies());
        } catch (NumberFormatException e){
            throw new InvalidUsersInputException("You didn't type proper number of species ");
        }
        if (numberOfSpecies < Constants.MIN_NUMBER_OF_SPECIES || numberOfSpecies > Constants.MAX_NUMBER_OF_SPECIES) {
            throw new InvalidUsersInputException("You didn't type proper number of species ");
        }
        return numberOfSpecies;
    }

    private void setNewTerritoryMenuAndClosePresent(String specialInfo) {
        new TerritoryMenuController(new TerritoryMenu(specialInfo));
        territoryMenu.close();
    }

    private int collectAndValidateFertility() throws InvalidUsersInputException {
        String fertility = territoryMenu.getFertility();
        return FertilityTextInt.valueOf(fertility.toUpperCase()).getFertilityNumber();
    }

    private void goToSpeciesMenu(TerritoryDTO territoryDTO) {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO));
        territoryMenu.close();
    }
}
