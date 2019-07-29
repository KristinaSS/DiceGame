package dicegame.elements;

public final class Combinations {
    public static int calcuatePair(int pairDie) {
        return (pairDie * 2) + CombinationEnum.PAIR.getValue();
    }

    public static int calcuateDoublePair(int pair1, int pair2) {
        return (2 * (pair1 + pair2)) + CombinationEnum.DOUBLE_PAIR.getValue();
    }

    public static int calcuateTriple(int tripleDie) {
        return (3 * tripleDie) + CombinationEnum.TRIPLE.getValue();
    }

    public static int calcuateFullHouse(int pair, int triple) {
        return (2 * pair) + (3 * triple) + CombinationEnum.FULL_HOUSE.getValue();
    }

    public static int calcuateStraight(boolean isTypeA) {
        if (isTypeA)
            return 15 + CombinationEnum.STRAIGHT.getValue();
        else
            return 20 + CombinationEnum.STRAIGHT.getValue();
    }

    public static int calcuateFourOfAKind(int quadruple) {
        return (4 * quadruple) + CombinationEnum.FOUR_OF_A_KIND.getValue();
    }

    public static int calcuateGenerala(int generalaDie) {
        return (5 * generalaDie) + CombinationEnum.GENERALA.getValue();
    }

    public static CombinationEnum getEnumWithIndex(int i) throws NullPointerException {
        //i =10;
        for (CombinationEnum e : CombinationEnum.values()) {
            if (e.INDEX == i)
                return e;
        }
        throw new NullPointerException("No Combination Enum with index " + i + " exist");
    }
}
