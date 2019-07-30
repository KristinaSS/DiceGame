package dicegame.elements;


public enum CombinationEnum implements Calculable {
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

    @Override
    public int calculateCombination(int dieNumber1, int dieNumber2) {
        switch (this.getIndex()){
            case 0:
                return (dieNumber1 * 2) + CombinationEnum.PAIR.getValue();
            case 1:
                return (2 * (dieNumber1 + dieNumber2)) + CombinationEnum.DOUBLE_PAIR.getValue();
            case 2:
                return (3 * dieNumber1) + CombinationEnum.TRIPLE.getValue();
            case 3:
                return (2 * dieNumber1) + (3 * dieNumber2) + CombinationEnum.FULL_HOUSE.getValue();
            case 4:
                if (dieNumber1== 1)
                    return 15 + CombinationEnum.STRAIGHT.getValue();
                else
                    return 20 + CombinationEnum.STRAIGHT.getValue();
            case 5:
                return (4 * dieNumber1) + CombinationEnum.FOUR_OF_A_KIND.getValue();
            case 6:
                return (5 * dieNumber1) + CombinationEnum.GENERALA.getValue();
            default:
                System.out.println("Something went wronge XD");
        }
        return 0;
    }
}
