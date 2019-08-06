package dicegame.elements;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public static int numberOfDice;
    public static int numberOfSides;
    private static StringBuilder diceRolledStr = new StringBuilder();

    private static Map<Integer,Integer> timesRepeatedEachDieSideTreeMap = new TreeMap<>(Collections.reverseOrder());

    private Dice() {
    }

    //Utils

    public static void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            int in = ThreadLocalRandom.current().nextInt(numberOfSides);

            diceRolledStr.append(in+1).append(" ");

            int count = timesRepeatedEachDieSideTreeMap.getOrDefault(in + 1, 0);
            timesRepeatedEachDieSideTreeMap.put(in + 1, count + 1);
        }
    }

    public static void resetDice() {
        diceRolledStr.setLength(0);
        timesRepeatedEachDieSideTreeMap.clear();
    }

    public static StringBuilder getDiceRolledStr() {
        return diceRolledStr;
    }

    public static Map<Integer, Integer> getTimesRepeatedEachDieSideTreeMap() {
        return timesRepeatedEachDieSideTreeMap;
    }
}
