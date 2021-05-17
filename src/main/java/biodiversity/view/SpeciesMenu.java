package biodiversity.view;


import biodiversity.controller.TerritoryDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SpeciesMenu extends Stage {

    private TerritoryDTO territoryDTO;

    private VBox layout;
    private Scene scene;

    private Text general;
    private HBox onColor;
    private Text colorExplained;
    private Rectangle colored;

    private Text onFeeding;
    private HBox buttonsFeeding;
    private ToggleGroup toggleFeeding;
    private RadioButton buttonFeeding1;
    private RadioButton buttonFeeding2;

    private Text onReplication;
    private HBox buttonsReplications;
    private ToggleGroup toggleReplication;
    private RadioButton buttonReplication1;
    private RadioButton buttonReplication2;
    private RadioButton buttonReplication3;
    private RadioButton buttonReplication4;

    private Text onMass;
    private Slider mass;

    private Text onMaxAge;
    private Slider maxAge;

    private Text onMaturityAge;
    private Slider maturityAge;

    private HBox backOrProceed;
    private Button back;
    private Button proceed;

    //TODO refactor the constructor
    // TODO SceneBuilder

    public SpeciesMenu(TerritoryDTO territoryDTO) {

        this.territoryDTO = territoryDTO;
        setTitle("Species Menu");
        layout = new VBox(30);
        layout.setAlignment(Pos.BASELINE_LEFT);
        layout.setPadding(new Insets(40));
        scene = new Scene(layout);
        setScene(scene);

        onColor = new HBox(20);
        colored =new Rectangle(30,15, CharColorFX.findColorByNumber(territoryDTO.getOrdinalNumberOfSpeciesToBeCreated()));
        colorExplained = new Text("species no "+territoryDTO.getOrdinalNumberOfSpeciesToBeCreated()+". creatures will appear this color on simulation");

        onColor.getChildren().addAll(colored, colorExplained);

        general = new Text("please note: system does prevent from creating species incapable to live\n" +
                "or from dominate the ecosystem by one species. carnivores will appear later during simulation");

        onFeeding = new Text("Select feeding strategy");

        buttonsFeeding = new HBox(20);
        toggleFeeding = new ToggleGroup();
        buttonFeeding1 = new RadioButton(DisplayConstants.HERBIVORE);
        buttonFeeding2 = new RadioButton(DisplayConstants.CARNIVORE);
        buttonFeeding1.setToggleGroup(toggleFeeding);
        buttonFeeding2.setToggleGroup(toggleFeeding);
        buttonsFeeding.getChildren().addAll(buttonFeeding1, buttonFeeding2);

        onReplication = new Text("Select replication strategy");
        buttonsReplications = new HBox(20);
        toggleReplication = new ToggleGroup();
        buttonReplication1 = new RadioButton(DisplayConstants.REPLICATION_DEFAULT);
        buttonReplication2 = new RadioButton(DisplayConstants.BIG_CHILDREN);
        buttonReplication3 = new RadioButton(DisplayConstants.SMALL_CHILDREN);
        buttonReplication4 = new RadioButton(DisplayConstants.MANY_SMALL_AT_END);
        buttonReplication1.setToggleGroup(toggleReplication);
        buttonReplication2.setToggleGroup(toggleReplication);
        buttonReplication3.setToggleGroup(toggleReplication);
        buttonReplication4.setToggleGroup(toggleReplication);
        buttonsReplications.getChildren().addAll(buttonReplication1, buttonReplication2, buttonReplication3, buttonReplication4);

        onMass = new Text("set pref mass of adult creature");
        mass = new Slider(10, 200, 70);
        mass.setMajorTickUnit(5);
        mass.setShowTickMarks(true);
        mass.setShowTickLabels(true);

        onMaxAge = new Text("set max age");
        maxAge = new Slider(20, 400, 150);
        maxAge.setMajorTickUnit(10);
        maxAge.setShowTickMarks(true);
        maxAge.setShowTickLabels(true);

        onMaturityAge = new Text("set maturity age");
        maturityAge = new Slider(10, 200, 40);
        maturityAge.setMajorTickUnit(5);
        maturityAge.setShowTickMarks(true);
        maturityAge.setShowTickLabels(true);

        createBackOrProceedButtons();


        layout.getChildren().addAll(onColor, general, onFeeding, buttonsFeeding, onReplication, buttonsReplications,
                onMass, mass, onMaxAge, maxAge, onMaturityAge, maturityAge, backOrProceed);
        show();
    }

    private void createBackOrProceedButtons() {
        backOrProceed = new HBox(20);
        back = new Button("Back to main menu");
        proceed = new Button("Proceed");
        backOrProceed.getChildren().addAll(back, proceed);
    }

    public void addBackButtonAction(EventHandler<ActionEvent> event){
        back.setOnAction(event);
    }

    public void addProceedButtonAction(EventHandler<ActionEvent> event){
        proceed.setOnAction(event);
    }

    public String getFeeding() throws Exception{
        if (toggleFeeding.getSelectedToggle()!=null){
            RadioButton chosen = (RadioButton) toggleFeeding.getSelectedToggle();
            return chosen.getText();
        } else {
            throw new Exception();
        }
    }

    public String getReplication() throws Exception{
        if (toggleReplication.getSelectedToggle()!=null){
            RadioButton chosen = (RadioButton) toggleReplication.getSelectedToggle();
            return chosen.getText();
        } else {
            throw new Exception();
        }
    }

    public int getMass() {
        return (int)mass.getValue();
    }

    public int getMaxAge(){
        return (int) maxAge.getValue();
    }

    public int getMaturityAge(){
        return (int) maturityAge.getValue();
    }


}
