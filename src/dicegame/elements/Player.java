package dicegame.elements;

import java.util.*;

public class Player {
    private int playerNumber;
    private int score = 0;
    private boolean [] playedCombinations = new boolean[7];
    private static List<Integer> diceRolled = new ArrayList<>();
    private static Map<Integer,Integer> sortScore = new HashMap<>();

    public static Map<Integer, Integer> getSortScore() {
        return sortScore;
    }

    public static void setSortScore(Map<Integer, Integer> sortScore) {
        Player.sortScore = sortScore;
    }

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean[] getPlayedCombinations() {
        return playedCombinations;
    }

    public void setPlayedCombinations(boolean[] playedCombinations) {
        this.playedCombinations = playedCombinations;
    }

    public List<Integer> getDiceRolled() {
        return diceRolled;
    }

    public void setDiceRolled(List<Integer> diceRolled) {
        Player.diceRolled = diceRolled;
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
