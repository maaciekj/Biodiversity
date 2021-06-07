package biodiversity.view;

import biodiversity.DisplayConstants;
import biodiversity.controller.TerritoryDTO;
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

import java.util.ArrayList;
import java.util.List;

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
    private List<Label> speciesLabels;

    public SimulationDisplay(TerritoryObserver territoryObserverInput, TerritoryDTO territoryDTO) {
        territoryObserver = territoryObserverInput;
        columnCount = territoryObserver.getWidth();
        rowsCount = territoryObserver.getHeight();
        root = new Pane();
        animationPane = new Pane();
        territoryObserver.setDisplay(this);
        initAnimationPane(territoryObserver.getSigns());
        initStatisticsPane(territoryDTO);
        scene = new Scene(root);
        initStage();
        startTimer();
    }

    private void initStage() {
        setScene(scene);
        setWidth(columnCount * DisplayConstants.FIELD_SIZE + 15);
        setHeight(rowsCount * DisplayConstants.FIELD_SIZE + 60);
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

    private void initStatisticsPane(TerritoryDTO territoryDTO) {
        statisticsHBox = new HBox();
        statisticsHBox.setLayoutX(0);
        statisticsHBox.setLayoutY(rowsCount * DisplayConstants.FIELD_SIZE);
        statisticsHBox.setPrefHeight(35);
        statisticsHBox.setPrefWidth(columnCount * DisplayConstants.FIELD_SIZE);
        statisticsHBox.setBackground(new Background((new BackgroundFill(Paint.valueOf(CharColorFX.NONE.getColor().toString()), null, null))));
        iterationNumberLabel = new Label("Iteration: 0 ");
        iterationNumberLabel.setStyle("-fx-text-fill: white;");
        statisticsHBox.getChildren().add(iterationNumberLabel);
        speciesLabels = new ArrayList<>();
        for (int i = 0; i < territoryDTO.getSpeciesDTOs().size(); i++) {
            char letterToDisplay = (char) (65 + i);
            speciesLabels.add(new Label("  Species " + letterToDisplay + ": " + "0  "));
            speciesLabels.get(i).setStyle("-fx-text-fill: " + CharColorFX.findDescriptionBySign(territoryDTO.getSpeciesDTOs().get(i).getSign()) + ";");
        }
        statisticsHBox.getChildren().addAll(speciesLabels);
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
        String textToSet = " Iteration: " + iterationNumber + "  ";
        iterationNumberLabel.setText(textToSet);
    }

    private void updateOrganismsNumber() {
        for (int i = 0; i < speciesLabels.size(); i++) {
            char letterToDisplay = (char) (65 + i); // capital letters from A
            char letterToSearch = (char) (97 + i); // small letters from a
            speciesLabels.get(i).setText("Species " + letterToDisplay + ": " + territoryObserver.getNumberOfOrganismsOfSpecies(letterToSearch) + "  ");
        }
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







