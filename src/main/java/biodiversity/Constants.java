package biodiversity;

public class Constants {


    public static final int TERRITORY_HEIGHT_DEFAULT = 150; // normal 200

    public static final int TERRITORY_WIDTH_DEFAULT = 200; // normal 300

    public static final int MIN_NUMBER_OF_SPECIES = 1;

    public static final int MAX_NUMBER_OF_SPECIES = 6;

    public static final int FIELD_STORAGE_FACTOR = 5;

    public static final int NUMBER_OF_ORGANISMS_OF_SPECIES_AT_THE_BEGINNING = 5;

    public static final double ENERGY_CONSUMPTION_EXPONENT = 0.75;

    public static final int ENERGY_NEED_FOR_BODY_MASS = 5;

    public static final int ENERGY_EXTRACTED_FROM_BODY_MASS_BY_PREDATOR = 4;

    public static final int ENERGY_EXTRACTED_FROM_BODY_MASS_BY_ORGANISM_ITSELF = 3;

    public static final double BASIC_REPLICATION_PROBABILITY = 0.75;

    public static final double CARNIVORES_BASIC_EFFICIENCY = 0.65;

    public static final int HERBIVORES_DEMAND_FACTOR = 5;

    public static final int CARNIVORES_DEMAND_FACTOR = 20;

    public static final int CARNIVORES_APPEAR_AT_ITERATION = 600;

    public static double CHILD_TO_ADULT_MASS_RATIO_STANDARD = 0.10; // best 0.15

    public static double CHILD_TO_ADULT_MASS_RATIO_BIG_CHILDREN = 0.25; // best 0.25

    public static double CHILD_TO_ADULT_MASS_RATIO_SMALL_CHILDREN = 0.06; // best 0.03

    public static double CHILD_TO_ADULT_MASS_RATIO_MANY_SMALL_CHILDREN = 0.06; // best 0.05

    public static double MANY_SMALL_CHILDREN_STRATEGY_EFFICIENCY = 0.75;

    public static double MIN_MASS_FACTOR_TO_REPRODUCE = 0.8;

    public static double STORED_ENERGY_FACTOR_TO_START_GROWING = 2.5;

}
