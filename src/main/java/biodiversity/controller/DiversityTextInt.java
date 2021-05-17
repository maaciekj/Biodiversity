package biodiversity.controller;

public enum DiversityTextInt {

    NONE(0), LOW(1), MEDIUM(3), HIGH(5);

    private int diversityCode;

    DiversityTextInt(int diversityCode) {
        this.diversityCode = diversityCode;
    }

    public int getDiversityCode() {
        return diversityCode;
    }
}
