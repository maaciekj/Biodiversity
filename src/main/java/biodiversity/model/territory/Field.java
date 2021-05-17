package biodiversity.model.territory;

import biodiversity.model.Constants;

import java.util.Objects;

public class Field {

    private int ediblePlants; // should be 0 or positive
    private int growForTurn;  // should be 0 or positive
    private final int row;
    private final int col;

    public Field(int growForTurn, int row, int col) {
        ediblePlants = 0;
        this.growForTurn = growForTurn;
        this.row = row;
        this.col = col;
    }

    public void growPlants(){
        if(ediblePlants>growForTurn*Constants.FIELD_STORAGE_FACTOR){
            return;
        }
        ediblePlants = ediblePlants+growForTurn;
    }

    public void destroyPlants (int howMany){  // in case of a special event
        ediblePlants = Math.max(ediblePlants - howMany, 0);
    }

    public void destroyPlants(){
        ediblePlants = 0;
    }

    public int feed (int demand){
        if (ediblePlants-demand>=0){
            ediblePlants = ediblePlants-demand;
            return demand;
        } else {
            int availablePlants = ediblePlants;
            ediblePlants = 0;
            return availablePlants;
        }
    }

    @Override
    public String toString() {
        return "Field{" +
                "ediblePlants=" + ediblePlants +
                ", growForTurn=" + growForTurn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return row == field.row && col == field.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int getGrowForTurn() {
        return growForTurn;
    }

    public void setGrowForTurn(int growForTurn) {
        this.growForTurn = growForTurn;
    }

    public void changeGrowForTurnBuilding(int howMuch){
        growForTurn = growForTurn+howMuch;
    }

    public void changeGrowForTurnRuntime(int howMuch){
        growForTurn= Math.max(growForTurn+howMuch, 0);
    }

    public int getEdiblePlants() {
        return ediblePlants;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
