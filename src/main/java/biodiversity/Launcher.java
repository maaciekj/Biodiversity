package biodiversity;

import biodiversity.controller.MenuController;
import biodiversity.view.Menu;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Launcher extends Application {

    private static final Logger logger = LogManager.getLogger(Launcher.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("App starts");
        Menu menu = new Menu();
        MenuController menuController = new MenuController(menu);
    }
}
