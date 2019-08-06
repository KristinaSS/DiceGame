package dicegame.elements;

import java.util.*;

public final class Player {
    private int playerNumber;
    private int score = 0;
    private Set <CombinationEnum> playedCombinationsSet = new HashSet<>();

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

    public void setScore(int score) {
        this.score = score;
    }
}
