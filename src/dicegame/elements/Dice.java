package dicegame.elements;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public static int numberOfDice;
    public static int numberOfSides;
    public static List<Integer> diceRolledList = new ArrayList<>();
    public static HashMap<CombinationEnum,Integer> sortedScoreMap = new HashMap<>();

    private Dice() {
    }

    //Utils

    public static void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            //diceRolledList.add(i, random.nextInt(numberOfSides) + 1);
            diceRolledList.add(i,ThreadLocalRandom.current().nextInt(numberOfSides)+1);
        }
    }

    public static void resetDice() {
        diceRolledList.clear();
        sortedScoreMap.clear();
    }

    public static void sortDiceReverseOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.reverseOrder());
    }

    public static void sortDiceNaturalOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.naturalOrder());
    }
}
