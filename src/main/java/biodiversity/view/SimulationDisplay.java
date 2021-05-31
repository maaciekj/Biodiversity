package biodiversity.view;

import biodiversity.DisplayConstants;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimulationDisplay extends Stage {

    private static final Logger logger = LogManager.getLogger();
    private final TerritoryObserver territoryObserver;
    private final int columnCount;
    private final int rowsCount;
    private final Pane root;
    private final Pane animationPane;
    private final Scene scene;
    private AnimationTimer animationTimer;
    private HBox statisticsHBox;
    private Label iterationNumberLabel;
    private Label speciesLabelA;
    private Label speciesLabelB;
    private Label speciesLabelC;
    private Label speciesLabelD;
    private Label speciesLabelE;

    public SimulationDisplay(TerritoryObserver territoryObserverInput) {
        territoryObserver = territoryObserverInput;
        columnCount = territoryObserver.getWidth();
        rowsCount = territoryObserver.getHeight();
        root = new Pane();
        animationPane = new Pane();
        territoryObserver.addDisplay(this);
        initAnimationPane(territoryObserver.getSigns());
        initStatisticsPane();
        scene = new Scene(root);
        initStage();
        startTimer();
    }


    private void initStage() {
        setScene(scene);
        setWidth(columnCount * DisplayConstants.FIELD_SIZE + 15);
        setHeight(rowsCount * DisplayConstants.FIELD_SIZE + 30 + 30);
        setTitle("Biodiversity Game");
        show();
    }

    private void initAnimationPane(char[][] organismSigns) {
        logger.info("animation pane started");
        for (int i = 0; i < organismSigns.length; i++) {
            for (int j = 0; j < organismSigns[0].length; j++) {
                animationPane.getChildren().add(new OrganismView(j, i, calculateColor(organismSigns[i][j])));
            }
        }
        root.getChildren().add(animationPane);
        logger.info("animation pane complete");
    }

    private Color calculateColor(char organismSign) {
        return CharColorFX.findColorByChar(organismSign);
    }

    private void initStatisticsPane(){
        statisticsHBox = new HBox();
        statisticsHBox.setLayoutX(0);
        statisticsHBox.setLayoutY(rowsCount * DisplayConstants.FIELD_SIZE);
        statisticsHBox.setPrefHeight(25);
        statisticsHBox.setPrefWidth(columnCount * DisplayConstants.FIELD_SIZE);
        statisticsHBox.setBackground(new Background((new BackgroundFill(Paint.valueOf(CharColorFX.NONE.getColor().toString()), null, null))));
        iterationNumberLabel = new Label("Iteration: 0 ");
        iterationNumberLabel.setStyle("-fx-text-fill: white;");
        statisticsHBox.getChildren().add(iterationNumberLabel);
        speciesLabelA = new Label (" Species B: 0 ");
        speciesLabelA.setStyle("-fx-text-fill: "+CharColorFX.A.getColorDescription()+";");
        statisticsHBox.getChildren().add(speciesLabelA);
        speciesLabelB = new Label (" Species B: 0 ");
        speciesLabelB.setStyle("-fx-text-fill: "+CharColorFX.B.getColorDescription()+";");
        statisticsHBox.getChildren().add(speciesLabelB);
        speciesLabelC = new Label (" Species C: 0 ");
        speciesLabelC.setStyle("-fx-text-fill: "+CharColorFX.C.getColorDescription()+";");
        statisticsHBox.getChildren().add(speciesLabelC);
        speciesLabelD = new Label (" Species D: 0 ");
        speciesLabelD.setStyle("-fx-text-fill: "+CharColorFX.D.getColorDescription()+";");
        statisticsHBox.getChildren().add(speciesLabelD);
        speciesLabelE = new Label (" Species E: 0 ");
        speciesLabelE.setStyle("-fx-text-fill: "+CharColorFX.E.getColorDescription()+";");
        statisticsHBox.getChildren().add(speciesLabelE);
        root.getChildren().add(statisticsHBox);
    }


    private void startTimer() {
        animationTimer = new AnimationTimer() {
            long lastTime;
            @Override
            public void handle(long now) {
                if (now - lastTime > DisplayConstants.ANIMATION_TIME) {
                    lastTime = now;
                    updateTerritory();
                    updateIterationNumber();
                    updateOrganismsNumber();
                }
            }
        };
        animationTimer.start();
    }

    private void updateIterationNumber() {
        int iterationNumber = territoryObserver.getIteration();
        String textToSet = " Iteration: "+ iterationNumber +" ";
        iterationNumberLabel.setText(textToSet);
    }

    private void updateOrganismsNumber(){
        String textToSet1 = "Species A: "+territoryObserver.getNumberOfOrganismsOfSpecies('a')+" ";
        speciesLabelA.setText(textToSet1);
        String textToSet2 = "Species B: "+territoryObserver.getNumberOfOrganismsOfSpecies('b')+" ";
        speciesLabelB.setText(textToSet2);
        String textToSet3 = "Species C: "+territoryObserver.getNumberOfOrganismsOfSpecies('c')+" ";
        speciesLabelC.setText(textToSet3);
        String textToSet4 = "Species D: "+territoryObserver.getNumberOfOrganismsOfSpecies('d')+" ";
        speciesLabelD.setText(textToSet4);
        String textToSet5 = "Species E: "+territoryObserver.getNumberOfOrganismsOfSpecies('e')+" ";
        speciesLabelE.setText(textToSet5);
    }

    private void updateTerritory() {
        territoryObserver.initializeIteration();
    }

    public void changeColorOfAnElementInDisplay(int cordX, int cordY, char sign) {
        OrganismView toBeChanged = (OrganismView) animationPane.getChildren().get(calculateNumberInDisplay(cordX, cordY));
        toBeChanged.updateColor(calculateColor(sign));
    }

    private int calculateNumberInDisplay(int cordX, int cordY) {
        return (cordY * columnCount) + cordX;
    }

}







