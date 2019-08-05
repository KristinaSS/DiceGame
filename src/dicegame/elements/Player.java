package dicegame.elements;

import java.util.*;

public final class Player {
    private int playerNumber;
    private int score = 0;
    private Map <CombinationEnum,Integer> playedCombinations = new HashMap<>();

    //Methods

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Map<CombinationEnum, Integer> getPlayedCombinations() {
        return playedCombinations;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
