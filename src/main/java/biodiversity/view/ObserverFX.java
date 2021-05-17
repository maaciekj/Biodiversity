package biodiversity.view;

import biodiversity.model.territory.Territory;

public class ObserverFX implements TerritoryObserver {

    Territory territory;
    SimulationDisplay simulationDisplay;

    @Override
    public void addTerritory(Territory territory) {
        this.territory = territory;
    }

    @Override
    public void addDisplay(SimulationDisplay simulationDisplay) {
        this.simulationDisplay = simulationDisplay;
    }

    @Override
    public int getWidth() {
        return territory.getWidth();
    }

    @Override
    public int getHeight() {
        return territory.getHeight();
    }

    @Override
    public char[][] getSigns() {
        return territory.getTerritorySigns();
    }

    @Override
    public void initializeIteration() {
        territory.doItsTurn();
    }

    @Override
    public void updateDisplay(int row, int col, char sign) {
        simulationDisplay.changeColorOfAnElementInDisplay(col, row, sign);
    }

}
