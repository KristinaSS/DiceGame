package dicegame.elements;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {
    public static int numberOfDice;
    public static int numberOfSides;
    public static List<Integer> diceRolledList = new ArrayList<>();
    public static Map<CombinationEnum,Integer> sortedScoreMap = new HashMap<>();
    public static Map<Integer,Integer> listTemp = new TreeMap<>(Collections.reverseOrder());

    private Dice() {
    }

    //Utils

    public static void rollDice() {
        for (int i = 0; i < numberOfDice; i++) {
            int in = ThreadLocalRandom.current().nextInt(numberOfSides);

            diceRolledList.add(i, in + 1);

            int count = listTemp.getOrDefault(in + 1, 0);
            listTemp.put(in + 1, count + 1);
        }
    }

    public static void resetDice() {
        diceRolledList.clear();
        sortedScoreMap.clear();
        listTemp.clear();
    }

    public static void sortDiceNaturalOrder(List<Integer> diceRolled) {
        diceRolled.sort(Comparator.naturalOrder());
    }
}
