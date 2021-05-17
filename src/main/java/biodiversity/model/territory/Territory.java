package biodiversity.model.territory;

import biodiversity.model.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.view.TerritoryObserver;

import java.util.*;

public class Territory {

    char emptyFieldSign;
    Field[][] places;
    Organism[][] inhabitants;
    TerritoryObserver observer;
    Counter counter;
    List<Organism> carnivores = new ArrayList<>();// TODO Clear after use

    public Territory(char emptyFieldSign, int height, int width, Field[][] places, TerritoryObserver observer, Counter counter) {
        this.emptyFieldSign = emptyFieldSign;
        this.places = places;
        this.inhabitants = new Organism[height][width];
        this.observer = observer;
        this.counter = counter;
    }

    public void doItsTurn() {
        placesDoTheirTurn();
        List<Organism> organisms = new LinkedList<>();
        for (int row = 0; row < inhabitants.length; row++) {
            for (int col = 0; col < inhabitants[row].length; col++) {
                if (inhabitants[row][col] != null) {
                    organisms.add(inhabitants[row][col]);
                }
            }
        }
        Collections.shuffle(organisms);
        for (Organism organism : organisms) {
            organism.doItsTurn();
        }
        if (counter.getIterationNumber() == Constants.CARNIVORES_APPEAR_AT_ITERATION) {
            introduceCarnivores();
        }
        collectStatistics(); // TODO displaying at SimulationDisplay
        printStatistics(); // only testing feature
        counter.addIteration();
    }

    private void placesDoTheirTurn() {
        if (places != null) {
            for (int row = 0; row < places.length; row++) {
                for (int col = 0; col < places[row].length; col++) {
                    places[row][col].growPlants();
                }
            }
        }
    }

    private void printStatistics() {
        if (counter.getIterationNumber() % 100 == 0) { // only testing feature
            System.out.println(places[10][10].toString());
            try {
                System.out.println(inhabitants[10][10].toString());
            } catch (Exception e) {
                System.out.println("null at the place");
            }
            System.out.println("number of organisms " + getNumberOfOrganisms());
            System.out.println("iteration: " + counter.getIterationNumber());
            System.out.println(counter.getTime() + "\n");
            counter.setTime();
        }
    }

    private void introduceCarnivores() {
        if(carnivores.isEmpty()){
            return;
        }
        for (Organism carnivore : carnivores) {
            addInhabitant(carnivore);
        }
        carnivores.clear();
    }

    private void collectStatistics() {
        // TODO
    }

    public Organism getInhabitant(int row, int col) {
        return inhabitants[row][col];
    }

    private int getNumberOfOrganisms() {
        int numberOfOrganisms = 0;
        for (int row = 0; row < inhabitants.length; row++) {
            for (int col = 0; col < inhabitants[row].length; col++) {
                if (inhabitants[row][col] != null) {
                    numberOfOrganisms++;
                }
            }
        }
        return numberOfOrganisms;
    }

    public char[][] getTerritorySigns() {
        char[][] signs = new char[inhabitants.length][inhabitants[0].length];
        for (int row = 0; row < inhabitants.length; row++) {
            for (int col = 0; col < inhabitants[row].length; col++) {
                if (inhabitants[row][col] == null) {
                    signs[row][col] = emptyFieldSign;
                } else {
                    signs[row][col] = inhabitants[row][col].getSign();
                }
            }
        }
        return signs;
    }

    public void addInhabitant(Organism inhabitant) {
        inhabitants[inhabitant.getRow()][inhabitant.getCol()] = inhabitant;
        observer.updateDisplay(inhabitant.getRow(), inhabitant.getCol(), inhabitant.getSign());// observer.update
    }

    public void updateCoordinates(Organism inhabitant) {
        inhabitants[inhabitant.getRow()][inhabitant.getCol()] = inhabitant;
        observer.updateDisplay(inhabitant.getRow(), inhabitant.getCol(), inhabitant.getSign());
    }

    public void removeInhabitant(int row, int col) {
        observer.updateDisplay(row, col, emptyFieldSign);
        inhabitants[row][col] = null;
    }

    public List<Field> checkFreePlaces(int row, int col, int range) {
        // generating list of free fields in square ranging from the organism searching both rectangular and diagonal
        List<Field> freeFields = new ArrayList<>();
        for (int i = 0; i < 2 * range + 1; i++) {
            for (int j = 0; j < 2 * range + 1; j++) {
                if (placeIsFree(row + i - range, col + j - range)) {
                    freeFields.add(places[row + i - range][col + j - range]);
                }
            }
        }
        return freeFields;
    }

    private boolean placeIsFree(int row, int col) {
        if (placeIsOutOfTerritoryBoundaries(row, col)) {
            return false;
        }
        return inhabitants[row][col] == null;
    }

    public boolean placeIsOutOfTerritoryBoundaries(int row, int col) {
        if (row < 0) {
            return true;
        }
        if (col < 0) {
            return true;
        }
        if (row >= inhabitants.length) {
            return true;
        }
        return col >= inhabitants[row].length;
    }

    public List<Organism> checkOrganismsNearby(int row, int col, int range) {
        List<Organism> organismsNearby = new ArrayList<>();
        for (int i = 0; i < 2 * range + 1; i++) {
            for (int j = 0; j < 2 * range + 1; j++) {
                // (row+i-range!=row||col+j-range!=col) mechanism preventing including asking organism to list
                if (placeIsInhabited(row + i - range, col + j - range) && (row + i - range != row || col + j - range != col)) {
                    organismsNearby.add(inhabitants[row + i - range][col + j - range]);
                }
            }
        }
        return organismsNearby;
    }

    private boolean placeIsInhabited(int row, int col) {
        if (placeIsOutOfTerritoryBoundaries(row, col)) {
            return false;
        }
        return inhabitants[row][col] != null;
    }

    public int feedOnPlants(int row, int col, int demand) {
        return places[row][col].feed(demand);
    }

    public Field getField(int row, int col) {
        return places[row][col];
    }

    public int getWidth() {
        return inhabitants[0].length;
    }

    public int getHeight() {
        return inhabitants.length;
    }

    public int getLastIteration() {
        return counter.getIterationNumber();
    }

    public void setCarnivores(List<Organism> carnivores) {
        this.carnivores = carnivores;
    }

    @Override
    public String toString() {
        return "Territory{" +
                "emptyFieldSign=" + emptyFieldSign +
                ", places=" + Arrays.deepToString(places) +
                ", inhabitants=" + Arrays.deepToString(inhabitants) +
                '}';
    }

}