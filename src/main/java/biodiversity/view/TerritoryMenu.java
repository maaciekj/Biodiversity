package biodiversity.view;

import biodiversity.Constants;
import biodiversity.controller.FertilityTextInt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TerritoryMenu extends Stage {

    private VBox layout;
    private Scene scene;

    private Text onSpecies;
    private TextField howManySpecies;

    private Text onFertility;
    private HBox buttonsFertility;
    private ToggleGroup toggleFertility;
    private RadioButton buttonFertility1;
    private RadioButton buttonFertility2;
    private RadioButton buttonFertility3;
    private RadioButton buttonFertility4;

    private Text onDiversity;
    private HBox buttonsDiversity;
    private ToggleGroup toggleDiversity;
    private RadioButton buttonDiversity1;
    private RadioButton buttonDiversity2;
    private RadioButton buttonDiversity3;
    private RadioButton buttonDiversity4;

    private HBox backOrProceed;
    private Button back;
    private Button proceed;


    public TerritoryMenu() {
        layout = new VBox(30);
        layout.setAlignment(Pos.BASELINE_LEFT);
        layout.setPadding(new Insets(40));
        scene = new Scene(layout);
        setScene(scene);

        onSpecies = new Text("How many species you'd like to have at the beginning? (" + Constants.MIN_NUMBER_OF_SPECIES + "-" + Constants.MAX_NUMBER_OF_SPECIES + ")");

        howManySpecies = new TextField();

        onFertility = new Text();
        onFertility.setText("The higher fertility of the territory, " +
                "\n the more and the bigger creatures can survive");
        buttonsFertility = new HBox(20);
        toggleFertility = new ToggleGroup();
        buttonFertility1 = new RadioButton(FertilityTextInt.SPARSE.toString().toLowerCase());
        buttonFertility2 = new RadioButton(FertilityTextInt.LOW.toString().toLowerCase());
        buttonFertility3 = new RadioButton(FertilityTextInt.MEDIUM.toString().toLowerCase());
        buttonFertility4 = new RadioButton(FertilityTextInt.HIGH.toString().toLowerCase());
        buttonFertility1.setToggleGroup(toggleFertility);
        buttonFertility2.setToggleGroup(toggleFertility);
        buttonFertility3.setToggleGroup(toggleFertility);
        buttonFertility4.setToggleGroup(toggleFertility);
        buttonsFertility.getChildren().addAll(buttonFertility1, buttonFertility2, buttonFertility3, buttonFertility4);

        onDiversity = new Text("Variation of the fertility can be lower or higher");
        buttonsDiversity = new HBox(20);
        toggleDiversity = new ToggleGroup();
        buttonDiversity1 = new RadioButton("none");
        buttonDiversity2 = new RadioButton("low");
        buttonDiversity3 = new RadioButton("medium");
        buttonDiversity4 = new RadioButton("high");
        buttonDiversity1.setToggleGroup(toggleDiversity);
        buttonDiversity2.setToggleGroup(toggleDiversity);
        buttonDiversity3.setToggleGroup(toggleDiversity);
        buttonDiversity4.setToggleGroup(toggleDiversity);
        buttonsDiversity.getChildren().addAll(buttonDiversity1, buttonDiversity2, buttonDiversity3, buttonDiversity4);

        backOrProceed = new HBox(20);
        back = new Button("Back to main menu");
        proceed = new Button("Proceed");
        backOrProceed.getChildren().addAll(back, proceed);

        //proceed.setAlignment(Pos.BOTTOM_RIGHT); // TODO Make this alignment work
        layout.getChildren().addAll(onSpecies, howManySpecies, onFertility, buttonsFertility, onDiversity, buttonsDiversity, backOrProceed);
        show();
    }

    public void addBackButtonAction(EventHandler<ActionEvent> event) {
        back.setOnAction(event);
    }

    public void addProceedButtonAction(EventHandler<ActionEvent> event) {
        proceed.setOnAction(event);
    }

    public String getHowManySpecies() {
        return howManySpecies.getText();
    }

    public String getFertility() throws InvalidUsersInputException {
        if (toggleFertility.getSelectedToggle() != null) {
            RadioButton chosen = (RadioButton) toggleFertility.getSelectedToggle();
            return chosen.getText();
        } else {
            throw new InvalidUsersInputException("You didn't choose fertility level");
        }
    }

    public String getDiversity() throws InvalidUsersInputException {
        if (toggleDiversity.getSelectedToggle() != null) {
            RadioButton chosen = (RadioButton) toggleDiversity.getSelectedToggle();
            return chosen.getText();
        } else {
            throw new InvalidUsersInputException("You didn't choose diversity level");
        }
    }

}
