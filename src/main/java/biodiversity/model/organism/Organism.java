package biodiversity.model.organism;

import biodiversity.Constants;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class Organism {

    private final Species species; // here Behavior
    private final char sign; // get from species, frequently accessed performance
    private int row; // concrete organism
    private int col; // concrete organism
    private int iterationDone; // to prevent organism from doing new iteration after migrating
    private int age; // concrete organism
    private int activeBodyMass; // concrete organism -
    private int storedEnergy; // concrete organism
    private int energyConsumption; // once for turn calculated; frequently needed
    private final Territory territory;
    private final NumberGenerator numberGenerator;
    // many features are at Species - Behavior level

    public Organism(Species species, int activeBodyMass, int storedEnergy, Territory territory, NumberGenerator numberGenerator) {
        this.species = species;
        this.sign = species.getSign();
        this.iterationDone = -1;
        this.age = 0;
        this.activeBodyMass = activeBodyMass;
        this.storedEnergy = storedEnergy;
        this.energyConsumption = 0;
        this.territory = territory;
        this.numberGenerator = numberGenerator;
    }

    public void doItsTurn() {
        if (iterationDone >= territory.getLastIteration()) {
            return;
        }
        dieOfAge();
        getOlder();
        calculateEnergyConsumption();
        grow();
        species.doOutsourcedFunctions(this);
        consumeEnergy();
        dieOfStarving();
        setIterationDone();
    }

    private void dieOfAge() {
        if (age < getMaxAge()) {
            return;
        }
        territory.removeInhabitant(row, col);
    }

    public void getOlder() {
        age += 1;
    }

    private void calculateEnergyConsumption() {
        energyConsumption = (int) Math.round(Math.pow(activeBodyMass, Constants.ENERGY_CONSUMPTION_EXPONENT));
    }

    private void grow() {
        if (activeBodyMass >= getAdultPreferredBodyMass()) {
            return;
        }
        if (storedEnergy < getEnergyConsumption() * Constants.STORED_ENERGY_FACTOR_TO_START_GROWING) {
            return;
        }
        addBodyMass(1);
        consumeEnergy(Constants.ENERGY_NEED_FOR_BODY_MASS);
    }

    private void consumeEnergy() {
        storedEnergy = storedEnergy - getEnergyConsumption();
    }

    private void dieOfStarving() {
        if (storedEnergy < 0 || activeBodyMass <= 0) {
            territory.removeInhabitant(row, col);
        }
    }

    private void setIterationDone() {
        iterationDone = territory.getLastIteration();
    }

    public void addEnergy(int howMuch) {
        storedEnergy = storedEnergy + howMuch;
    }

    public void consumeEnergy(int howMuch) {
        storedEnergy = storedEnergy - howMuch;
    }

    public void subtractBodyMass(int howMuch) {
        activeBodyMass = activeBodyMass - howMuch;
    }

    public void addBodyMass(int howMuch) {
        activeBodyMass = activeBodyMass + howMuch;
    }

    // getters from fields

    public Species getSpecies() {
        return species;
    }

    public char getSign() {
        return sign;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getAge() {
        return age;
    }

    public int getActiveBodyMass() {
        return activeBodyMass;
    }

    public int getStoredEnergy() {
        return storedEnergy;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public Territory getTerritory() {
        return territory;
    }

    public NumberGenerator getNumberGenerator() {
        return numberGenerator;
    }

    // getters from Species

    public int getMaturityAge() {
        return species.getMaturityAge();
    }

    public int getMaxAge() {
        return species.getMaxAge();
    }

    public int getAdultPreferredBodyMass() {
        return species.getAdultPreferredBodyMass();
    }

    // setters

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Organism{" +
                "sign=" + sign +
                ", age=" + age +
                ", activeBodyMass=" + activeBodyMass +
                ", storedEnergy=" + storedEnergy +
                '}';
    }

}






