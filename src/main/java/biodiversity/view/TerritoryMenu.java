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

    private final TextField howManySpecies;
    private Text onFertility;
    private HBox buttonsFertility;
    private ToggleGroup toggleFertility;
    private Text onDiversity;
    private HBox buttonsDiversity;
    private ToggleGroup toggleDiversity;
    private HBox backOrProceed;
    private Button back;
    private Button proceed;

    public TerritoryMenu() {
        VBox layout = new VBox(30);
        layout.setAlignment(Pos.BASELINE_LEFT);
        layout.setPadding(new Insets(40));
        Scene scene = new Scene(layout);
        setScene(scene);
        Text onSpecies = new Text("How many species you'd like to have at the beginning? (" + Constants.MIN_NUMBER_OF_SPECIES + "-" + Constants.MAX_NUMBER_OF_SPECIES + ")");
        howManySpecies = new TextField();
        initializeFertilityBlock();
        initializeDiversityBlock();
        initializeBackOrProceedButtons();
        layout.getChildren().addAll(onSpecies, howManySpecies, onFertility, buttonsFertility, onDiversity, buttonsDiversity, backOrProceed);
        show();
    }

    private void initializeFertilityBlock() {
        onFertility = new Text();
        onFertility.setText("Select ground fertility. The higher fertility," +
                "\n the more and the bigger creatures can survive");
        buttonsFertility = new HBox(20);
        toggleFertility = new ToggleGroup();
        RadioButton buttonFertility1 = new RadioButton(FertilityTextInt.SPARSE.toString().toLowerCase());
        RadioButton buttonFertility2 = new RadioButton(FertilityTextInt.LOW.toString().toLowerCase());
        RadioButton buttonFertility3 = new RadioButton(FertilityTextInt.MEDIUM.toString().toLowerCase());
        RadioButton buttonFertility4 = new RadioButton(FertilityTextInt.HIGH.toString().toLowerCase());
        buttonFertility1.setToggleGroup(toggleFertility);
        buttonFertility2.setToggleGroup(toggleFertility);
        buttonFertility3.setToggleGroup(toggleFertility);
        buttonFertility4.setToggleGroup(toggleFertility);
        buttonsFertility.getChildren().addAll(buttonFertility1, buttonFertility2, buttonFertility3, buttonFertility4);
    }

    private void initializeDiversityBlock() {
        onDiversity = new Text("Select variation of ground fertility");
        buttonsDiversity = new HBox(20);
        toggleDiversity = new ToggleGroup();
        RadioButton buttonDiversity1 = new RadioButton("none");
        RadioButton buttonDiversity2 = new RadioButton("low");
        RadioButton buttonDiversity3 = new RadioButton("medium");
        RadioButton buttonDiversity4 = new RadioButton("high");
        buttonDiversity1.setToggleGroup(toggleDiversity);
        buttonDiversity2.setToggleGroup(toggleDiversity);
        buttonDiversity3.setToggleGroup(toggleDiversity);
        buttonDiversity4.setToggleGroup(toggleDiversity);
        buttonsDiversity.getChildren().addAll(buttonDiversity1, buttonDiversity2, buttonDiversity3, buttonDiversity4);
    }

    private void initializeBackOrProceedButtons() {
        backOrProceed = new HBox(20);
        back = new Button("Back to main menu");
        proceed = new Button("Proceed");
        backOrProceed.getChildren().addAll(back, proceed);
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
