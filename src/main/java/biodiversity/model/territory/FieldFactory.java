package biodiversity.model.territory;

import java.util.HashSet;
import java.util.Set;

public class FieldFactory {

    NumberGenerator numberGenerator;
    Field[][] fields;

    public FieldFactory(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    public Field[][] createFieldPattern(int height, int width, int baseFertility, int fertilityDiversity) {
        createBasicFields(height, width, baseFertility);
        if (fertilityDiversity == 0) {
            return fields;
        }
        int numberOfSpecialFields = calculateNumberOfSpecialFields(height, width);
        Set<Field> specialFields = new HashSet<>(); // to avoid two Fields with same row and col
        for (int i = 0; i < numberOfSpecialFields; i++) {
            specialFields.add(createSpecialField(baseFertility, fertilityDiversity));
        }
        addSpecialFieldToArrayAndChangeNearbyFields(baseFertility, specialFields);
        reduceFieldFertilityVariation(baseFertility);
        return fields;
    }

    private void createBasicFields(int height, int width, int baseFertility) {
        fields = new Field[height][width];
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                fields[i][j] = new Field(baseFertility, i, j);
            }
        }
    }

    private int calculateNumberOfSpecialFields(int height, int width) {
        double specialFieldsProbability = 0.02;
        return (int) Math.round(height * width * specialFieldsProbability);

    }

    private Field createSpecialField(int baseFertility, int fertilityDiversity) {
        int fertility;
        if (numberGenerator.generateBoolean()) {
            fertility = baseFertility + fertilityDiversity;
        } else {
            fertility = Math.max(baseFertility - fertilityDiversity, 0);
        }
        return new Field(fertility, numberGenerator.generateRandomInt(fields.length), numberGenerator.generateRandomInt(fields[0].length));
    }

    private void addSpecialFieldToArrayAndChangeNearbyFields(int baseFertility, Set<Field> specialFields) {
        for (Field specialField : specialFields) {
            fields[specialField.getRow()][specialField.getCol()] = specialField;
            for (int row = 0; row < fields.length; row++) {
                for (int col = 0; col < fields[row].length; col++) {
                    int baseRangeOfSpecialField = 3;
                    int furtherRangeOfSpecialField = baseRangeOfSpecialField * 2;
                    if (fieldIsInCircle(fields[row][col], specialField, baseRangeOfSpecialField)) {
                        fields[row][col].changeGrowForTurnBuilding(specialField.getGrowForTurn() - baseFertility);
                    } else {
                        if (fieldIsInCircle(fields[row][col], specialField, furtherRangeOfSpecialField)) {
                            fields[row][col].changeGrowForTurnBuilding((specialField.getGrowForTurn() - baseFertility) / 2);
                        }
                    }
                }
            }
        }
    }

    private boolean fieldIsInCircle(Field checked, Field center, int radius) {
        if (!fieldFitsToArray(checked)) {
            return false;
        }
        // area within a circle is: (x - a)^2 + (y - b)^2 <= r^2 from: https://linustechtips.com/topic/372049-java-and-analytic-geometry/
        return Math.pow(checked.getRow() - center.getRow(), 2) + Math.pow(checked.getCol() - center.getCol(), 2) <= Math.pow(radius, 2);
    }

    private boolean fieldFitsToArray(Field checked) {
        if (checked.getRow() < 0) {
            return false;
        }
        if (checked.getRow() >= fields.length) {
            return false;
        }
        if (checked.getCol() < 0) {
            return false;
        }
        return checked.getCol() < fields[0].length;
    }

    private void reduceFieldFertilityVariation(int baseFertility) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].getGrowForTurn() < 0) {
                    fields[i][j].setGrowForTurn(0);
                }
                if (fields[i][j].getGrowForTurn() > baseFertility * 3) {
                    fields[i][j].setGrowForTurn(baseFertility * 3);
                }
            }
        }
    }

}
