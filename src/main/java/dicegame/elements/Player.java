package dicegame.elements;

import dicegame.constants.CombinationEnum;

import java.util.Set;
import java.util.HashSet;

//todo JavaDoc
public final class Player implements Comparable<Player> {
    private final int playerNumber;

    private int score;

    private final Set<CombinationEnum> playedComboSet = new HashSet<>();

    //Methods
    public Player(final int playerCount) {
        this.playerNumber = playerCount;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Set<CombinationEnum> getPlayedComboSet() {
        return playedComboSet;
    }

    public int getScore() {
        return score;
    }

    void setScore(final int curScore) { //NOPMD
        this.score = curScore;
    }

    void rollDice() { //NOPMD
        DiceRolled.rollAllDice();
    }

    @Override
    public int compareTo(final Player player) {
        final boolean hasGeneralaO1 = this.getPlayedComboSet()
                                          .contains(CombinationEnum.GENERALA);
        final boolean hasGeneralaO2 = player.getPlayedComboSet()
                                            .contains(CombinationEnum.GENERALA);

        final int hasGenerala//NOPMD
                = Boolean.compare(hasGeneralaO2, hasGeneralaO1);

        if (hasGenerala != 0) {
            return hasGenerala; //NOPMD
        }

        return Integer.compare(player.getScore(), this.getScore());
    }
}
