package biodiversity.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Menu extends Stage {

    private VBox container;
    private HBox chooseBox;
    private Scene scene;
    private Button startDefault;
    private Button configure;
    private Button exit;

    public Menu() {
        setTitle("Biodiversity Game");
        container = new VBox();
        scene = new Scene(container);
        chooseBox = new HBox(10);
        chooseBox.setAlignment(Pos.CENTER);
        chooseBox.setPadding(new Insets(25));

        startDefault = new Button("Start default");
        configure = new Button("Configure");
        exit = new Button("EXIT");
        setScene(scene);
        chooseBox.getChildren().addAll(startDefault, configure, exit);
        container.getChildren().add(chooseBox);
        show();
    }

    public Menu(String specialInfo){
        setTitle("Biodiversity Game");

        chooseBox = new HBox(10);
        chooseBox.setAlignment(Pos.CENTER);
        chooseBox.setPadding(new Insets(25));
        scene = new Scene(chooseBox);
        startDefault = new Button("Start default");
        configure = new Button("Configure");
        exit = new Button("EXIT");
        setScene(scene);
        chooseBox.getChildren().addAll(startDefault, configure, exit);
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
