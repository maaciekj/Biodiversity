package biodiversity.model.organism;

import biodiversity.Constants;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class Organism {

    private final Species species; // here Behavior
    private final char sign; // get from species, frequently accessed performance issue
    private int row; // concrete organism
    private int col; // concrete organism
    private int age; // concrete organism
    private int activeBodyMass; // concrete organism -
    private int storedEnergy; // concrete organism
    private int energyConsumption; // once for turn calculated; frequently needed performance issue
    private final Territory territory;
    private final NumberGenerator numberGenerator;
    // many features are at Species - Behavior level

    public static class Builder {
        private Species species;
        private int activeBodyMass;
        private int storedEnergy;
        private Territory territory;
        private NumberGenerator numberGenerator;

        public Builder species(Species species){
            this.species = species;
            return this;
        }

        public Builder activeBodyMass(int activeBodyMass){
            this.activeBodyMass = activeBodyMass;
            return this;
        }

        public Builder storedEnergy(int storedEnergy){
            this.storedEnergy = storedEnergy;
            return this;
        }

        public Builder territory(Territory territory){
            this.territory = territory;
            return this;
        }

        public Builder numberGenerator(NumberGenerator numberGenerator){
            this.numberGenerator = numberGenerator;
            return this;
        }

        public Organism build(){
            return new Organism(this);
        }
    }

    public Organism(Builder builder) {
        this.species = builder.species;
        this.sign = species.getSign();
        this.age = 0;
        this.activeBodyMass = builder.activeBodyMass;
        this.storedEnergy = builder.storedEnergy;
        this.energyConsumption = 0;
        this.territory = builder.territory;
        this.numberGenerator = builder.numberGenerator;
    }

    public void doItsTurn() {
        dieOfAge();
        getOlder();
        calculateEnergyConsumption();
        grow();
        species.doOutsourcedFunctions(this);
        consumeEnergy();
        dieOfStarving();
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






