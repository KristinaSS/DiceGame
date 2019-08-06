package dicegame.elements;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public static int numberOfDice;
    public static int numberOfSides;
    private static StringBuilder diceRolledStr = new StringBuilder();
    private static Map<CombinationEnum,Integer> sortedScoreMap = new HashMap<>();
    private static Map<Integer,Integer> bucketSortTreeMap = new TreeMap<>(Collections.reverseOrder());

    private Dice() {
    }

    //Utils

    public static void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            int in = ThreadLocalRandom.current().nextInt(numberOfSides);

            diceRolledStr.append(in+1).append(" ");

            int count = bucketSortTreeMap.getOrDefault(in + 1, 0);
            bucketSortTreeMap.put(in + 1, count + 1);
        }
    }

    public static void resetDice() {
        diceRolledStr.setLength(0);
        sortedScoreMap.clear();
        bucketSortTreeMap.clear();
    }

    public static StringBuilder getDiceRolledStr() {
        return diceRolledStr;
    }

    public static Map<CombinationEnum, Integer> getSortedScoreMap() {
        return sortedScoreMap;
    }

    public static void setSortedScoreMap(Map<CombinationEnum, Integer> sortedScoreMap) {
        Dice.sortedScoreMap = sortedScoreMap;
    }

    public static Map<Integer, Integer> getBucketSortTreeMap() {
        return bucketSortTreeMap;
    }

}
