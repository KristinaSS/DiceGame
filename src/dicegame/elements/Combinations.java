package dicegame.elements;

public final class Combinations {

    private static final CombinationsEnum[] TYPE_COMBINATION = CombinationsEnum.values();

    //Calculating

    public static int calcuatePair(int pairDie){
        return (pairDie*2)+CombinationsEnum.PAIR.getValue();
    }

    public static int calcuateDoublePair(int pair1, int pair2){
        return (2*(pair1+pair2))+CombinationsEnum.DOUBLE_PAIR.getValue();
    }

    public static int calcuateTriple(int tripleDie){
        return (3*tripleDie)+CombinationsEnum.TRIPLE.getValue();
    }

    public static int calcuateFullHouse(int pair, int triple){
        return (2*pair)+ (3*triple) + CombinationsEnum.FULL_HOUSE.getValue();
    }

    public static int calcuateStraight(boolean isTypeA){
        if(isTypeA)
            return 15 + CombinationsEnum.STRAIGHT.getValue();
        else
            return 20 + CombinationsEnum.STRAIGHT.getValue();
    }

    public static int calcuateFourOfAKind(int quadruple){
        return (4*quadruple)+ CombinationsEnum.FOUR_OF_A_KIND.getValue();
    }

    public static int calcuateGenerala(int generalaDie){
        return (5*generalaDie)+ CombinationsEnum.GENERALA.getValue();
    }

    public static CombinationsEnum[] getTypeCombination() {
        return TYPE_COMBINATION;
    }
}
