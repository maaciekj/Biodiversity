package biodiversity.view;

import biodiversity.model.territory.Territory;

// TODO separate interface for input form animation components

public interface TerritoryObserver {

    void addTerritory(Territory territory);

    void addDisplay(SimulationDisplay simulationDisplay);

    int getWidth();

    int getHeight();

    char [][] getSigns();

    void initializeIteration();

    void updateDisplay(int row, int col, char sign);

}
