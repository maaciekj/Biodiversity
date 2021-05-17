package biodiversity.controller;

import biodiversity.model.Constants;
import biodiversity.view.Menu;
import biodiversity.view.SpeciesMenu;
import biodiversity.view.TerritoryMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TerritoryMenuController {

    private TerritoryMenu territoryMenu;

    public TerritoryMenuController(TerritoryMenu territoryMenu) {
        this.territoryMenu = territoryMenu;
        addBackAction();
        addProceedAction();
    }

    private void addBackAction() {
        territoryMenu.addBackButtonAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MenuController(new Menu());
                territoryMenu.close();
            }
        });
    }

    private void addProceedAction() {
        territoryMenu.addProceedButtonAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("proceeding"); // TODO gathering info to TerritoryDTO and pass to Species Menu
                TerritoryDTO territoryDTO = new TerritoryDTO();
                try {
                    territoryDTO.setNumberOfSpecies(collectAndValidateNumberOfSpecies());
                } catch (Exception e){
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
            }
        });
    }

    private int collectAndValidateNumberOfSpecies() throws Exception{
        int numberOfSpecies = Integer.parseInt(territoryMenu.getHowManySpecies());
        if (numberOfSpecies< Constants.MIN_NUMBER_OF_SPECIES ||numberOfSpecies>Constants.MAX_NUMBER_OF_SPECIES){
            throw new Exception();
        }
        return numberOfSpecies;
    }

    private void setNewTerritoryMenuAndClosePresent(){
        new TerritoryMenuController(new TerritoryMenu());
        territoryMenu.close();
    }

    private int collectAndValidateFertility() throws Exception {
        String fertility = territoryMenu.getFertility();
        return FertilityTextInt.valueOf(fertility.toUpperCase()).getFertilityNumber();
    }

    private void goToSpeciesMenu(TerritoryDTO territoryDTO) {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO), territoryDTO);
        System.out.println(territoryDTO);
        territoryMenu.close();
    }
}
