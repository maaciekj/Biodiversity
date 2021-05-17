package biodiversity.controller;

import biodiversity.view.CharColorFX;
import biodiversity.view.Menu;
import biodiversity.view.SpeciesMenu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SpeciesMenuController {

    private final SpeciesMenu speciesMenu;
    private final TerritoryDTO territoryDTO;

    public SpeciesMenuController(SpeciesMenu speciesMenu, TerritoryDTO territoryDTO) {
        this.speciesMenu = speciesMenu;
        this.territoryDTO = territoryDTO;
        addBackAction();
        addProceedAction();
    }

    private void addBackAction() {
        speciesMenu.addBackButtonAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new MenuController(new Menu());
                speciesMenu.close();
            }
        });
    }

    private void addProceedAction(){
        speciesMenu.addProceedButtonAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("proceeding");
                SpeciesDTO speciesDTO = new SpeciesDTO();
                speciesDTO.setSign(CharColorFX.findSignByNumber(territoryDTO.getOrdinalNumberOfSpeciesToBeCreated()));
                BehaviorDTO behaviorDTO = new BehaviorDTO();
                try {
                    behaviorDTO.setFeedingStrategy(speciesMenu.getFeeding());
                } catch (Exception e){
                    setNewSpeciesMenu();
                    return;
                }
                try{
                    behaviorDTO.setReplicationStrategy(speciesMenu.getReplication());
                } catch (Exception e){
                    setNewSpeciesMenu();
                    return;
                }

                speciesDTO.setBehaviorDTO(behaviorDTO);
                EvolutionaryLineDTO evolutionaryLineDTO = new EvolutionaryLineDTO(speciesDTO.getSign(), speciesMenu.getMass(), speciesMenu.getMaturityAge(), speciesMenu.getMaxAge());
                speciesDTO.setEvolutionaryLineDTO(evolutionaryLineDTO);
                System.out.println(speciesDTO);
                territoryDTO.addSpeciesDTO(speciesDTO);
                if (territoryDTO.getNumberOfSpecies()==territoryDTO.getSpeciesDTOs().size()){
                    proceedToSimulation();
                } else {
                    setNewSpeciesMenu();
                }
            }
        });
    }

    private void setNewSpeciesMenu() {
        new SpeciesMenuController(new SpeciesMenu(territoryDTO), territoryDTO);
        System.out.println(territoryDTO);
        speciesMenu.close();
    }

    private void proceedToSimulation(){
        System.out.println(territoryDTO);
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.startSimulation(territoryDTO);
        speciesMenu.close();
    }

}
