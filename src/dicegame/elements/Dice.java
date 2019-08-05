package dicegame.elements;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    private int numberOfDice;
    private int numberOfSides;

    private static Dice diceInstance = null;
   // private static ThreadLocalRandom random = new ThreadLocalRandom();
    private static List<Integer> diceRolled = new ArrayList<>();
    private static HashMap<CombinationEnum,Integer> sortedScore = new HashMap<>();

    //Methods
    public static HashMap<CombinationEnum, Integer> getSortedScore() {
        return sortedScore;
    }

    public static void setSortedScore(HashMap<CombinationEnum, Integer> sortedScore) {
        Dice.sortedScore = sortedScore;
    }

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

    public static List<Integer> getDiceRolled() {
        return diceRolled;
    }


    //Utils

    public void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            //diceRolled.add(i, random.nextInt(numberOfSides) + 1);
            diceRolled.add(i,ThreadLocalRandom.current().nextInt(numberOfSides)+1);
        }
    }

    public void resetDice() {
        diceRolled.clear();
    }

    public void sortDiceReverseOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.reverseOrder());
    }

    public void sortDiceNaturalOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.naturalOrder());
    }
}
