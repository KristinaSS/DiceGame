package dicegame.elements;


public enum CombinationEnum {
    PAIR(0, 10, "Pair"),
    DOUBLE_PAIR(1, 15, "Double Pair"),
    TRIPLE(2, 20, "Triple"),
    FULL_HOUSE(3, 25, "Full House"),
    STRAIGHT(4, 30, "straight"),
    FOUR_OF_A_KIND(5, 40, "Four of a Kind"),
    GENERALA(6, 50, "Generala");

    int INDEX;
    int constValue;
    String LABEL;

    CombinationEnum(int index, int value, String label) {
        INDEX = index;
        LABEL = label;
        constValue = value;
    }

    public int getValue() {
        return constValue;
    }

    public String getLabel() throws NullPointerException {
        return LABEL;
    }

    public int getIndex() {
        return INDEX;
    }
}
