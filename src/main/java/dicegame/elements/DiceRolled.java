package dicegame.elements;

import java.io.IOException;
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

//todo javadoc
public final class DiceRolled { //NOPMD//NOPMD
    private static int numberOfDice;

    private static List<Die> diceRolled = new ArrayList<>();

    private static Map<Integer, Integer> diceRepeated
            = new TreeMap<>(Collections.reverseOrder());

    private DiceRolled() {
    }

    public static Map<Integer, Integer> getDiceRepeated() { //NOPMD
        return diceRepeated; //NOPMD
    }

    public static int getNumberOfDice() {
        return numberOfDice;
    }

    static public void setNumberOfDice() throws IOException {
        DiceRolled.numberOfDice = Integer.parseInt(GameUtils
                .getPropertyFromPropFile(CommonConstants.DICE_COUNT_STR));

        if (numberOfDice < 1) {
            throw new IllegalCountException("Your dice count"
                    + " needs to be larger than 0");
        }

        for (int i = 0; i < numberOfDice; i++) {
            diceRolled.add(new Die()); //NOPMD
        }
    }

    // other methods

    static void rollAllDice() { //NOPMD
        diceRepeated.clear();
        //NOPMD
        int dieSide;

        for (final Die die : diceRolled) {
            dieSide = die.rollDie();

            diceRepeated.put(dieSide, diceRepeated
                    .getOrDefault(dieSide, 0) + 1);
        }

    }

    static String rolledDiceListToString() { //NOPMD
        diceRolled.sort(Comparator.comparingInt(Die::getDieSide));
        final StringBuilder diceRolledSB = new StringBuilder(" ");
        for (final Die die : diceRolled) { //NOPMD//NOPMD//NOPMD
            diceRolledSB.append(die.getDieSide())
                        .append(", ");
        }
        return diceRolledSB.toString();
    }

    public static int findFirstValueGreaterThanOrEqualTo(final int compareBy) {
        //NOPMD
        for (final Map.Entry<Integer, Integer> entry : diceRepeated
                .entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey(); //NOPMD
            }
        }
        return -1;
    }

    public static class Die { //NOPMD//NOPMD
        private int dieSide;

        private static int numberOfSides; //NOPMD

        public static void setNumberOfSides() throws IOException {
            numberOfSides = Integer
                    .parseInt(GameUtils.readPropertiesFile()//NOPMD
                                       .getProperty(CommonConstants
                                               .SIDES_ON_DICE));
        }

        Die() { //NOPMD
        }

        public static int getNumberOfSides() {
            return numberOfSides;
        }

        final int getDieSide() {//NOPMD
            return dieSide;
        }

        final int rollDie() { //NOPMD
            dieSide = ThreadLocalRandom.current()
                                       .nextInt(numberOfSides) + 1;
            return dieSide;
        } //NOPMD

    }

}
