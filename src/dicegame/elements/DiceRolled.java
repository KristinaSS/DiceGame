package dicegame.elements;

import dicegame.interfaces.GameElementActions;

import java.util.*;

public class DiceRolled  implements GameElementActions {
    public static int numberOfDice;

    private static DiceRolled diceRolledInstance = null;

    private final static List<Die> diceRolled = new ArrayList<>();
    private final static Map<Integer,Integer> timesRepeatedEachDice = new TreeMap<>(Collections.reverseOrder());

    //getters and setters and constructors

    private DiceRolled() {
    }

    public static DiceRolled getInstance() {
        if (diceRolledInstance == null)
            diceRolledInstance = new DiceRolled();
        return diceRolledInstance;
    }

    public static Map<Integer, Integer> getTimesRepeatedEachDice() {
        return timesRepeatedEachDice;
    }

    //implemented methods

    public void playElement() {
        Die dice;
        int dieSide;
        for (int i = 0; i < numberOfDice; i++) {
             dice = new Die();
             dieSide = dice.getDieSide();
             diceRolled.add(dice);

             timesRepeatedEachDice.put(dieSide,
                    timesRepeatedEachDice.getOrDefault(dieSide, 0) + 1);
        }

    }

    public void resetElement() {
        diceRolled.clear();
        timesRepeatedEachDice.clear();
    }

    //other Methods

    public static String rolledDiceListToString(){
        diceRolled.sort(Comparator.comparingInt(Die::getDieSide));
        StringBuilder diceRolledSB = new StringBuilder(" ");
        for(Die die: diceRolled){
            diceRolledSB.append(die.getDieSide()).append(", ");
        }
        return diceRolledSB.toString();
    }

    public static int findFirstValueGreaterThanOrEqualTo(int compareBy) {
        for (Map.Entry<Integer, Integer> entry : timesRepeatedEachDice.entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
