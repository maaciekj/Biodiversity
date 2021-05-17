package biodiversity.view;

import biodiversity.model.territory.Territory;

public class ObserverForConsole implements TerritoryObserver {

    @Override
    public void addTerritory(Territory territory) {
    }

    @Override
    public void addDisplay(SimulationDisplay simulationDisplay) {
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public char[][] getSigns() {
        return new char[0][];
    }

    @Override
    public void initializeIteration() {
    }

    @Override
    public void updateDisplay(int row, int col, char sign) {
    }
}
