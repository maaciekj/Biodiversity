package biodiversity.model.territory;

import biodiversity.Constants;
import biodiversity.model.organism.Organism;
import biodiversity.view.TerritoryObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Territory {

    private static final Logger logger = LogManager.getLogger(Territory.class);

    private char emptyFieldSign;
    private Field[][] places;
    private Organism[][] inhabitants;
    private TerritoryObserver observer;
    private Counter counter;
    private List<Organism> carnivores = new ArrayList<>();

    public static class Builder {
        private char emptyFieldSign;
        private Field[][] places;
        private int height;
        private int width;
        private TerritoryObserver observer;
        private Counter counter;

        public Builder emptyFieldSign(char emptyFieldSign) {
            this.emptyFieldSign = emptyFieldSign;
            return this;
        }

        public Builder fields(Field[][] places) {
            this.places = places;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder observer(TerritoryObserver observer) {
            this.observer = observer;
            return this;
        }

        public Builder counter(Counter counter) {
            this.counter = counter;
            return this;
        }

        public Territory build() {
            Territory territory = new Territory();
            territory.emptyFieldSign = this.emptyFieldSign;
            territory.places = this.places;
            territory.inhabitants = new Organism[this.height][this.width];
            territory.observer = this.observer;
            territory.counter = this.counter;
            territory.carnivores = new ArrayList<>();
            return territory;
        }
    }

    private Territory() {
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
        printStatistics();
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
            logger.info("number of organisms " + getNumberOfOrganisms());
            logger.info("iteration: " + counter.getIterationNumber());
            logger.info(counter.getTime() + "\n");
            counter.setTime();
        }
    }

    private void introduceCarnivores() {
        if (carnivores.isEmpty()) {
            return;
        }
        for (Organism carnivore : carnivores) {
            addInhabitant(carnivore);
        }
        carnivores.clear();
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