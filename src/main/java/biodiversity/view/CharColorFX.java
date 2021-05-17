package biodiversity.view;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.*;

public enum CharColorFX {

    NONE(0, ' ', Color.color(0.15, 0.15, 0.15)),
    A(1, 'a', CORNFLOWERBLUE), B(2, 'b', DARKORANGE), C(3, 'c', LEMONCHIFFON), D(4, 'd', OLIVE), E(5, 'e', CRIMSON),
    F(6, 'f', SLATEGRAY), G(7, 'g', CADETBLUE), H(8, 'h', PALEGREEN), I(8, 'i', BLUEVIOLET), J(9, 'j', DARKKHAKI);

    private int number;
    private char sign;
    private Color color;

    CharColorFX(int number, char sign, Color color) {
        this.number = number;
        this.sign = sign;
        this.color = color;
    }

    private static final Map<Character, Color> COLORS_BY_CHARS = new HashMap<>();

    static {
        for (CharColorFX e : values()) {
            COLORS_BY_CHARS.put(e.sign, e.color);
        }
    }

    public static Color findColorByChar(char sign) {
        return COLORS_BY_CHARS.get(sign);
    }

    private static final Map<Integer, Color> COLORS_BY_NUMBERS = new HashMap<>();

    static {
        for (CharColorFX e : values()) {
            COLORS_BY_NUMBERS.put(e.number, e.color);
        }
    }

    public static Color findColorByNumber(int number) {
        return COLORS_BY_NUMBERS.get(number);
    }

    private static final Map<Integer, Character> CHARS_BY_NUMBERS = new HashMap<>();

    static {
        for (CharColorFX e : values()) {
            CHARS_BY_NUMBERS.put(e.number, e.sign);
        }
    }

    public static char findSignByNumber(int number) {
        return CHARS_BY_NUMBERS.get(number);
    }

    public char getSign() {
        return sign;
    }

    public Color getColor() {
        return color;
    }

}
