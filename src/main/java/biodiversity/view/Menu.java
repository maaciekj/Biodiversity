package biodiversity.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menu extends Stage {

    private HBox layout;
    private Scene scene;
    private Button startDefault;
    private Button configure;
    private Button exit;

    public Menu() {
        setTitle("Biodiversity Game");
        layout = new HBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        scene = new Scene(layout);
        startDefault = new Button("Start default");
        configure = new Button("Configure");
        exit = new Button("EXIT");
        setScene(scene);
        layout.getChildren().addAll(startDefault, configure, exit);
        show();
    }


    public void addStartDefaultButtonAction(EventHandler<ActionEvent> event) {
        startDefault.setOnAction(event);
    }

    public void addConfigButtonAction(EventHandler<ActionEvent> event) {
        configure.setOnAction(event);
    }

    public void addExitButtonAction(EventHandler<ActionEvent> event) {
        exit.setOnAction(event);
    }

}
