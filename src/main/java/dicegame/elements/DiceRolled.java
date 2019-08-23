package dicegame.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import dicegame.constants.CommonConstants;
import dicegame.exceptions.IllegalCountException;
import dicegame.utils.GameUtils;

public class DiceRolled {
    private static int numberOfDice;

    private final static List<Die> diceRolled = new ArrayList<>();

    private final static Map<Integer, Integer> timesRepeatedEachDice = new TreeMap<>(Collections.reverseOrder());


    private DiceRolled() {
    }

    static Map<Integer, Integer> getTimesRepeatedEachDice() {
        return timesRepeatedEachDice;
    }

    public static int getNumberOfDice() {
        return numberOfDice;
    }

    public static void setNumberOfDice() {
        DiceRolled.numberOfDice = Integer.parseInt(GameUtils.getPropertyFromPropFile(CommonConstants.DICE_COUNT_STR));

        if(numberOfDice<1)
            throw new IllegalCountException("Your dice count needs to be larger than 0");

        for (int i = 0; i < numberOfDice; i++) {
            diceRolled.add(new Die());
        }
    }

    // other methods

    static void rollAllDice() {
        timesRepeatedEachDice.clear();

        int dieSide;

        for (Die die : diceRolled) {
            dieSide = die.rollDie();

            timesRepeatedEachDice.put(dieSide, timesRepeatedEachDice.getOrDefault(dieSide, 0) + 1);
        }

    }

    static String rolledDiceListToString() {
        diceRolled.sort(Comparator.comparingInt(Die::getDieSide));
        StringBuilder diceRolledSB = new StringBuilder(" ");
        for (Die die : diceRolled) {
            diceRolledSB.append(die.getDieSide()).append(", ");
        }
        return diceRolledSB.toString();
    }

    static int findFirstValueGreaterThanOrEqualTo(int compareBy) {
        for (Map.Entry<Integer, Integer> entry : timesRepeatedEachDice.entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey();
            }
        }
        return -1;
    }
    static class Die {
        private int dieSide;

        static final int numberOfSides = Integer
                .parseInt(GameUtils.readPropertiesFile().getProperty(CommonConstants.NUMBER_OF_SIDES_STR));

        Die() {
        }

        int getDieSide() {
            return dieSide;
        }

        int rollDie() {
            dieSide = ThreadLocalRandom.current().nextInt(numberOfSides) + 1;
            return dieSide;
        }

    }

}
