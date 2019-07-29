package dicegame.elements;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Dice {
    private static Random random = new Random();
    private static final int NUMBER_OF_DICES = 5;
    private static final int NUMBER_OF_SIDES = 6;

    private static Dice diceInstance = null;

    public static Dice getInstance() {
        if (diceInstance == null)
            diceInstance = new Dice();
        return diceInstance;
    }

    private Dice() {
    }

    public void rollDice(Player player) {
        for (int i = 0; i < NUMBER_OF_DICES; i++) {
            player.getDiceRolled().add(i, random.nextInt(NUMBER_OF_SIDES) + 1);
        }
    }

    public void resetDice(Player player) {
        player.getDiceRolled().clear();
    }

    public void sortDice(Player player) {
        player.getDiceRolled().sort(Comparator.naturalOrder());
    }
}
