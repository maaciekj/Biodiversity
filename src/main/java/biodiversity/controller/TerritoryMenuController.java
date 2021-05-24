package biodiversity.controller;

import biodiversity.Constants;
import biodiversity.view.InvalidUsersInputException;
import biodiversity.view.Menu;
import biodiversity.view.SpeciesMenu;
import biodiversity.view.TerritoryMenu;

public class TerritoryMenuController {

    private TerritoryMenu territoryMenu;

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
            System.out.println("proceeding");
            TerritoryDTO territoryDTO = new TerritoryDTO();
            try {
                territoryDTO.setNumberOfSpecies(collectAndValidateNumberOfSpecies());
            } catch (InvalidUsersInputException e){
                setNewTerritoryMenuAndClosePresent();
                return;
            }
            try {
                territoryDTO.setFertility(collectAndValidateFertility());
            } catch (Exception e) {
                setNewTerritoryMenuAndClosePresent();
                return;
            }
            try {
                String diversity = territoryMenu.getDiversity();
                territoryDTO.setFertilityDiversity(DiversityTextInt.valueOf(diversity.toUpperCase()).getDiversityCode());
            } catch (Exception e) {
                setNewTerritoryMenuAndClosePresent();
                return;
            }
            goToSpeciesMenu(territoryDTO);
        });
    }

    private int collectAndValidateNumberOfSpecies() throws InvalidUsersInputException {
        int numberOfSpecies = Integer.parseInt(territoryMenu.getHowManySpecies());
        if (numberOfSpecies< Constants.MIN_NUMBER_OF_SPECIES ||numberOfSpecies>Constants.MAX_NUMBER_OF_SPECIES){
            throw new InvalidUsersInputException("You didn't type number of species ");
        }
        return numberOfSpecies;
    }

    private void setNewTerritoryMenuAndClosePresent(){
        new TerritoryMenuController(new TerritoryMenu());
        territoryMenu.close();
    }

    private int collectAndValidateFertility() throws InvalidUsersInputException {
        String fertility = territoryMenu.getFertility();
        return FertilityTextInt.valueOf(fertility.toUpperCase()).getFertilityNumber();
    }

    private void goToSpeciesMenu(TerritoryDTO territoryDTO) {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO));
        System.out.println(territoryDTO);
        territoryMenu.close();
    }
}
