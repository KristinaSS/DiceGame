package dicegame.elements;

public final class Combinations {
    private static final int  PAIR_CONS = 10;
    private static final int  DOUBLE_PAIR_CONS = 15;
    private static final int  TRIPLE_CONS = 20;
    private static final int  FULL_HOUSE_CONS = 25;
    private static final int  STRAIGHT_CONS = 30;
    private static final int  FOUR_OF_A_KIND_CONS = 40;
    private static final int  GENERALA_CONS = 50;

    private static final String[] TYPE_COMBINATION = {"Pair",
            "Double Pair","Triple","Full House", "Straight", "Four of a Kind", "Generala"};

    //todo think about return types and make by Musala format
    //do the reading file thing

    public static int pair(int pairDie){
        return (pairDie*2)+PAIR_CONS;
    }

    public static int doublePair(int pair1, int pair2){
        return (2*(pair1+pair2))+DOUBLE_PAIR_CONS;
    }

    public static int triple(int tripleDie){
        return (3*tripleDie)+TRIPLE_CONS;
    }

    public static int fullHouse(int pair, int triple){
        return (2*pair)+ (3*triple) + FULL_HOUSE_CONS;
    }

    public static int straight(boolean isTypeA){
        if(isTypeA)
            return 15 + STRAIGHT_CONS;
        else
            return 20 + STRAIGHT_CONS;
    }

    public static int fourOfaKind(int quadruple){
        return (4*quadruple)+ FOUR_OF_A_KIND_CONS;
    }

    public static int generala(int generalaDie){
        return (5*generalaDie)+ GENERALA_CONS;
    }

    public static String[] getTypeCombination() {
        return TYPE_COMBINATION;
    }
}
