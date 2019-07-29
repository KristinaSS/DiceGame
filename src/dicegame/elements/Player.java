package dicegame.elements;

import java.util.*;

public final class Player {
    private int playerNumber;
    private int score = 0;
    private Map<CombinationEnum, Boolean> playedCombinations = new HashMap<>();

    private static List<Integer> diceRolled = new ArrayList<>();
    private static Map<CombinationEnum, Integer> sortScore = new HashMap<>();

    //Methods

    public static Map<CombinationEnum, Integer> getSortScore() {
        return sortScore;
    }

    public static void setSortScore(Map<CombinationEnum, Integer> sortScore) {
        Player.sortScore = sortScore;
    }

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        for (CombinationEnum e : CombinationEnum.values())
            playedCombinations.put(e, false);
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Map<CombinationEnum, Boolean> getPlayedCombinations() {
        return playedCombinations;
    }

    public void setPlayedCombinations(Map<CombinationEnum, Boolean> playedCombinations) {
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
