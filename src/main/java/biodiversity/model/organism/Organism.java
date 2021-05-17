package biodiversity.model.organism;

import biodiversity.model.Constants;
import biodiversity.model.territory.NumberGenerator;
import biodiversity.model.territory.Territory;

public class Organism {

    private Species species; // here maybe Behavior and Replication Strategy
    private final char sign; // get from species
    private EvolutionaryLine evolutionaryLine; // has all typical characteristics and functions
    private int row; // concrete organism
    private int col; // concrete organism
    private int lastRow;
    private int lastCol;
    private int iterationDone; // to prevent organism from doing new iteration after migrating
    private boolean healthy; // concrete organism
    private int age; // concrete organism
    private int activeBodyMass; // concrete organism - adult pref. body mass - ev Line  method grow(), method extractEnergy()
    // predator must have greater ABM than prey ->
    private int storedEnergy; // concrete organism
    private int energyConsumption;
    private final Territory territory;
    private NumberGenerator numberGenerator;


    // many other features - some at species, most at evolutionary line level

    public Organism(Species species, EvolutionaryLine evolutionaryLine, int activeBodyMass, int storedEnergy, Territory territory, NumberGenerator numberGenerator) {
        this.species = species;
        this.sign = species.getSign();
        this.evolutionaryLine = evolutionaryLine;
        this.lastRow = -1;
        this.lastCol = -1;
        this.iterationDone = -1;
        this.healthy = true;
        this.age = 0;
        this.activeBodyMass = activeBodyMass;
        this.storedEnergy = storedEnergy;
        this.energyConsumption = 0;
        this.territory = territory;
        this.numberGenerator = numberGenerator;
    }


    public void doItsTurn () {
        if (iterationDone>=territory.getLastIteration()){
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

    private void dieOfAge(){
        if (age<getMaxAge()){
            return;
        }
        territory.removeInhabitant(row, col);
    }

    public void getOlder() {
        age += 1;
    }

    private void calculateEnergyConsumption(){
        energyConsumption = (int)Math.round(Math.pow(activeBodyMass, Constants.ENERGY_CONSUMPTION_EXPONENT));
    }

    private void grow() {
        if (activeBodyMass>=getAdultPreferredBodyMass()){
            return;
        }
        if (!healthy){
            return;
        }
        if (storedEnergy<getEnergyConsumption()*Constants.STORED_ENERGY_FACTOR_TO_START_GROWING){
            return;
        }
        addBodyMass(1);
        consumeEnergy(Constants.ENERGY_NEED_FOR_BODY_MASS);
    }

    private void consumeEnergy(){
        storedEnergy=storedEnergy- getEnergyConsumption();
    }

    private void dieOfStarving(){
        if (storedEnergy<0||activeBodyMass<=0){
            territory.removeInhabitant(row, col);
        }
    }

    private void setIterationDone() {
        iterationDone = territory.getLastIteration();
    }

    private void extractEnergyFromBodyMass(Organism organism){
        if(organism.getStoredEnergy()>organism.getEnergyConsumption()){
            return;
        }
        organism.subtractBodyMass(Math.round((organism.getEnergyConsumption())-organism.getStoredEnergy())/Constants.ENERGY_EXTRACTED_FROM_BODY_MASS_BY_ORGANISM_ITSELF);
        organism.addEnergy(organism.getEnergyConsumption()-organism.getStoredEnergy());
    }


    public void addEnergy(int howMuch){
        storedEnergy=storedEnergy+howMuch;
    }

    public void consumeEnergy(int howMuch){
        storedEnergy=storedEnergy-howMuch;
    }

    public void subtractBodyMass(int howMuch){
        activeBodyMass = activeBodyMass-howMuch;
    }

    public void addBodyMass(int howMuch){
        activeBodyMass=activeBodyMass+howMuch;

    }

    // getters from fields

    public Species getSpecies() {
        return species;
    }

    public char getSign() {
        return sign;
    }

    public EvolutionaryLine getEvolutionaryLine() {
        return evolutionaryLine;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIterationDone() {
        return iterationDone;
    }

    public boolean isHealthy() {
        return healthy;
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

    // getters from Evolutionary line

    public int getMaturityAge() {
        return evolutionaryLine.getMaturityAge();
    }

    public int getMaxAge() {
        return evolutionaryLine.getMaxAge();
    }

    public int getAdultPreferredBodyMass(){
        return evolutionaryLine.getAdultPreferredBodyMass();
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
                ", healthy=" + healthy +
                ", age=" + age +
                ", activeBodyMass=" + activeBodyMass +
                ", storedEnergy=" + storedEnergy +
                '}';
    }

}






