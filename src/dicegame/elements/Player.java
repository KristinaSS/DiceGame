package dicegame.elements;

import java.util.*;

public final class Player {
    private int playerNumber;
    private int score = 0;
    private List<CombinationEnum> playedCombinations = new ArrayList<>();

    //Methods

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public List<CombinationEnum> getPlayedCombinations() {
        return playedCombinations;
    }

    public void setPlayedCombinations(List<CombinationEnum> playedCombinations) {
        this.playedCombinations = playedCombinations;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
