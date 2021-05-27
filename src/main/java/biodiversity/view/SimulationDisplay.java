package biodiversity.view;


import biodiversity.DisplayConstants;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SimulationDisplay extends Stage {

    private int columnCount;
    private int rowsCount;
    //window  // TODO separate branches for main simulationDisplay and additional displays and controllers
    private Pane root = new Pane();
    private Scene scene = new Scene(root);
    private AnimationTimer animationTimer;
    //game elements
    private TerritoryObserver territoryObserver;
    // tablica

    public SimulationDisplay(TerritoryObserver territoryInput) {
        //  scene.setOnMouseClicked((mouseEvent)->{
        //    eventGenerator.createMeteor(mouseEvent.getX(),mouseEvent.getY());
        //});
        territoryObserver = territoryInput;
        territoryObserver.addDisplay(this);
        columnCount = territoryObserver.getWidth();
        rowsCount = territoryObserver.getHeight();
        initStage();
        initComponents(territoryObserver.getSigns());
        startTimer();
    }

    private void initStage() {
        setScene(scene);
        setWidth(columnCount * DisplayConstants.FIELD_SIZE + 15);
        setHeight(rowsCount * DisplayConstants.FIELD_SIZE + 30);
        setTitle("Biodiversity Game");
        show();
    }

    private void initComponents(char[][] organismSigns) {
        for (int i = 0; i < organismSigns.length; i++) {
            for (int j = 0; j < organismSigns[0].length; j++) {
                root.getChildren().add(new OrganismView(j, i, calculateColor(organismSigns[i][j])));
            }
        }
    }

    private Color calculateColor(char organismSign) {
        return CharColorFX.findColorByChar(organismSign);
    }

    private void startTimer() {
        animationTimer = new AnimationTimer() {
            long lastTime;
            @Override
            public void handle(long now) {
                if (now - lastTime > DisplayConstants.ANIMATION_TIME) {
                    lastTime = now;
                    updateTerritory();
                }
            }
        };
        animationTimer.start();
    }

    private void updateTerritory() {
        for (int i = 0; i < DisplayConstants.ITERATIONS_FOR_SCREEN; i++) {  // TODO simplify this method or make really configurable
            territoryObserver.initializeIteration();
        }
    }

    public void changeColorOfAnElementInDisplay(int cordX, int cordY, char sign) {
        OrganismView toBeChanged = (OrganismView) root.getChildren().get(calculateNumberInDisplay(cordX, cordY));
        toBeChanged.updateColor(calculateColor(sign));
    }

    private int calculateNumberInDisplay(int cordX, int cordY) {
        /*if (cordX >= columnCount || cordY >= rowsCount) {
            *//*try {
                throw new Exception("Given coordinates greater than territory size");
            } catch (Exception e) {
                e.printStackTrace();
            }*//*
        }*/
        return (cordY * columnCount) + cordX;
    }

}







