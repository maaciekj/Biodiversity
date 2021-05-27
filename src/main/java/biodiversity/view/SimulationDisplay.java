package biodiversity.view;


import biodiversity.DisplayConstants;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SimulationDisplay extends Stage {

    private static Logger logger = LogManager.getLogger();

    private TerritoryObserver territoryObserver;
    private int columnCount;
    private int rowsCount;

    private Pane root;
    private Pane animationPane;
    private Scene scene;
    private AnimationTimer animationTimer;

    private int iterationNumber;

    private Pane iterationNumberDisplay;


    public SimulationDisplay(TerritoryObserver territoryObserverInput) {
        //  scene.setOnMouseClicked((mouseEvent)->{
        //    eventGenerator.createMeteor(mouseEvent.getX(),mouseEvent.getY());
        //});
        territoryObserver = territoryObserverInput;
        columnCount = territoryObserver.getWidth();
        rowsCount = territoryObserver.getHeight();
        root = new Pane();
        animationPane = new Pane();
        territoryObserver.addDisplay(this);
        initAnimationPane(territoryObserver.getSigns());
        initComponents();
        addIterationNumber();
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



    private void initComponents() {
        root.getChildren().add(animationPane);

    }

    private void initAnimationPane(char[][] organismSigns) {
        logger.info("animation pane started");
        for (int i = 0; i < organismSigns.length; i++) {
            for (int j = 0; j < organismSigns[0].length; j++) {
                animationPane.getChildren().add(new OrganismView(j, i, calculateColor(organismSigns[i][j])));
            }
        }
        logger.info("animation pane complete");
    }

    private Color calculateColor(char organismSign) {
        return CharColorFX.findColorByChar(organismSign);
    }

    private void addIterationNumber(){
        iterationNumberDisplay = new Pane();
        iterationNumberDisplay.setLayoutX(10);
        iterationNumberDisplay.setLayoutY(450);
        iterationNumberDisplay.getChildren().add(new Label("Iteration: "+iterationNumber));
        root.getChildren().add(iterationNumberDisplay);
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
                }
            }
        };
        animationTimer.start();
    }

    private void updateIterationNumber() {
        iterationNumber = territoryObserver.getIteration();
        //iterationNumberDisplay.getChildren().get(0).
                //.setAccessibleText("Iteration: "+iterationNumber);
        iterationNumberDisplay.getChildren().clear();
        iterationNumberDisplay.getChildren().add(new Label("Iteration: "+iterationNumber));

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







