package biodiversity.controller;

import biodiversity.view.Menu;
import biodiversity.view.TerritoryMenu;

public class MenuController {

    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
        addButtonsActions();
    }

    public void addButtonsActions() {
        menu.addStartDefaultButtonAction(event -> startDefaultAction());
        menu.addConfigButtonAction(event -> configAction());
        menu.addExitButtonAction(event -> exitAction());
    }

    private void startDefaultAction() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.startDefaultSimulation();
        menu.close();
    }

    private void configAction() {
        TerritoryMenu territoryMenu = new TerritoryMenu();
        new TerritoryMenuController(territoryMenu);
        menu.close();
    }

    private void exitAction() {
        System.exit(0);
    }
}
