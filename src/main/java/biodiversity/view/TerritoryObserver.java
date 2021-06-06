package biodiversity.view;

import biodiversity.model.territory.Territory;

public interface TerritoryObserver {

    void addTerritory(Territory territory);

    void setDisplay(SimulationDisplay simulationDisplay);

    int getWidth();

    int getHeight();

    char[][] getSigns();

    void initializeIteration();

    void updateDisplay(int row, int col, char sign);

    int getNumberOfOrganismsOfSpecies(char sign);

    int getIteration();

}
