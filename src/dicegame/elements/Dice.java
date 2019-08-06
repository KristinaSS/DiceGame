package dicegame.elements;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public static int numberOfDice;
    public static int numberOfSides;
    public static List<Integer> diceRolledList = new ArrayList<>();
    public static HashMap<CombinationEnum,Integer> sortedScoreMap = new HashMap<>();
    public static Map<Integer,Integer> listTemp = new HashMap<>();

    private Dice() {
    }

    //Utils

    public static void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            diceRolledList.add(i,ThreadLocalRandom.current().nextInt(numberOfSides)+1);
            int in = ThreadLocalRandom.current().nextInt(numberOfSides);
            if (listTemp.containsKey(in))
                listTemp.computeIfPresent(in, (k, v) -> v + 1);
            else listTemp.put(in, 1);
        }
    }

    public static void resetDice() {
        diceRolledList.clear();
        sortedScoreMap.clear();
        listTemp.clear();
    }

    public static void sortDiceReverseOrder(List<Integer> diceRolled) {
        System.out.println(listTemp);
        diceRolled.sort(Comparator.reverseOrder());
    }

    public static void sortDiceNaturalOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.naturalOrder());
    }
}
