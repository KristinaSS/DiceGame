package dicegame.elements;

import java.util.*;

public class Dice {
    private int numberOfDice;
    private int numberOfSides;

    private static Dice diceInstance = null;
    private static Random random = new Random();
    private static List<Integer> diceRolled = new ArrayList<>();
    private static Map<CombinationEnum, Integer> sortScore = new HashMap<>();

    //Methods

    public static Dice getInstance() {
        if (diceInstance == null)
            diceInstance = new Dice();
        return diceInstance;
    }

    private Dice() {
    }

    public void setNumberOfDices(int numberOfDices) {
        this.numberOfDice = numberOfDices;
    }

    public void setNumberOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public int getNumberOfDice() {
        return numberOfDice;
    }

    public int getNumberOfSides() {
        return numberOfSides;
    }

    public static List<Integer> getDiceRolled() {
        return diceRolled;
    }

    public static void setDiceRolled(List<Integer> diceRolled) {
        Dice.diceRolled = diceRolled;
    }

    public static Map<CombinationEnum, Integer> getSortScore() {
        return sortScore;
    }

    public static void setSortScore(Map<CombinationEnum, Integer> sortScore) {
        Dice.sortScore = sortScore;
    }

    //Utils

    public void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            diceRolled.add(i, random.nextInt(numberOfSides) + 1);
        }
    }

    public void resetDice() {
        diceRolled.clear();
    }

    public void sortDiceReverseOrder() {
        diceRolled.sort(Comparator.reverseOrder());
    }

    public void sortDiceNaturalOrder() {
        diceRolled.sort(Comparator.naturalOrder());
    }
}
