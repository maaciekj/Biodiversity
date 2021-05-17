package biodiversity.controller;

public enum FertilityTextInt {

    SPARSE(3), LOW(5), MEDIUM(7), HIGH(9);

    private final int fertilityNumber;

    FertilityTextInt(int fertilityNumber) {
        this.fertilityNumber = fertilityNumber;
    }

    public int getFertilityNumber() {
        return fertilityNumber;
    }
}
