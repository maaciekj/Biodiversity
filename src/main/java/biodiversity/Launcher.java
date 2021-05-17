package biodiversity;

import biodiversity.controller.MenuController;
import biodiversity.view.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Menu menu = new Menu();
        MenuController menuController = new MenuController(menu);
    }
}
