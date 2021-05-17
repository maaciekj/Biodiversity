package biodiversity.view;

import biodiversity.DisplayConstants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OrganismView extends Rectangle {

    int cordX;
    int cordY;
    final int size = DisplayConstants.FIELD_SIZE;

    public OrganismView(int cordX, int cordY, Color color) {
        this.cordX = cordX;
        this.cordY = cordY;
        setHeight(size);
        setWidth(size);
        setFill(color);
        setX(cordX * size);
        setY(cordY * size);
    }

    public void updateColor(Color color) {
        setFill(color);
    }

    @Override
    public String toString() {
        return "OrganismView{" +
                "cordX=" + cordX +
                ", cordY=" + cordY +
                ", size=" + size + " color" + getFill() +
                '}';
    }
}
