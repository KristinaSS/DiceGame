package dicegame.elements;

import dicegame.constants.CombinationEnum;

import java.util.*;

public final class Player implements Comparable<Player> {
    private int playerNumber;

    private int score = 0;

    private Set<CombinationEnum> playedCombinationsSet = new HashSet<>();

    //Methods
    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Set<CombinationEnum> getPlayedCombinationsSet() {
        return playedCombinationsSet;
    }

    public int getScore() {
        return score;
    }

    void setScore(int score) {
        this.score = score;
    }

    void rollDice() {
        DiceRolled.rotateElement();
    }

    @Override
    public int compareTo(Player o) {
        boolean hasGeneralaO1 = this.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);
        boolean hasGeneralaO2 = o.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);

        int boolHasGeneralaCompare = Boolean.compare(hasGeneralaO2, hasGeneralaO1);

        if (boolHasGeneralaCompare != 0)
            return boolHasGeneralaCompare;

        return Integer.compare(o.getScore(), this.getScore());
    }
}
