package dicegame.elements;

import java.util.*;

public class Dice {
    private int numberOfDice;
    private int numberOfSides;

    private static Dice diceInstance = null;
    private static Random random = new Random();
    private static List<Integer> diceRolled = new ArrayList<>();

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

    public static List<Integer> getDiceRolled() {
        return diceRolled;
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
