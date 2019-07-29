package dicegame.elements;

import java.util.ArrayList;
import java.util.List;

public enum CombinationsEnum {
    PAIR(10,"Pair"),
    DOUBLE_PAIR(15, "Double Pair"),
    TRIPLE(20,"Triple"),
    FULL_HOUSE(25, "Full House"),
    STRAIGHT(30, "straight"),
    FOUR_OF_A_KIND(40,"Four of a Kind"),
    GENERALA(50,"Generala");

    int constValue;
    String LABEL;

    CombinationsEnum (int value, String label) {
        LABEL = label;
        constValue = value;
    }

    public int getValue() {
        return constValue;
    }

    public String getLabel(){
        return LABEL;
    }
}
