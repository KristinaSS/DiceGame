package dicegame.application;

import dicegame.elements.Combinations;
import dicegame.GameUtils;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;

class Game extends GameUtils {
    private int roundsLeft;
    private final int playerCount;

    Game(int rounds, int playerCount) {
        this.roundsLeft = rounds;
        this.playerCount = playerCount;
    }

    private GameUtils gameUtils = GameUtils.getInstance();

    void playGame() throws NullPointerException {
        int initialRounds = roundsLeft;
        List<Player> playerList = gameUtils.fillPlayerList(playerCount);
        Dice dice = Dice.getInstance();

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        while (roundsLeft-- > 0) {
            System.out.println();

            for (Player p : playerList) {
                dice.resetDice(p);
                dice.rollDice(p);
                dice.sortDice(p);
                evaluate(p, (-(roundsLeft - initialRounds)));

                if (p.getPlayedCombinations().get(CombinationEnum.GENERALA)) {
                    gameUtils.endGame(playerList, p);
                    return;
                }
                System.out.println();
            }
        }
        gameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player p, int round) throws NullPointerException {
        int oldScore = p.getScore();
        CombinationEnum entryKey;
        int entryValue;

        Player.getSortScore().put(CombinationEnum.PAIR, checkPair(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.DOUBLE_PAIR, checkDoublePair(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.TRIPLE, checkTriple(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.FULL_HOUSE, checkFullHouse(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.STRAIGHT, checkStraight(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.FOUR_OF_A_KIND, checkFourOfaKind(p.getDiceRolled()));

        Player.getSortScore().put(CombinationEnum.GENERALA, checkGenerala(p.getDiceRolled()));

        Player.setSortScore(gameUtils.sortByValue());

        for (int i = 0; i < CombinationEnum.values().length; i++) {
            entryKey = gameUtils.getEntry(i).getKey();
            entryValue = gameUtils.getEntry(i).getValue();
            if (entryValue > 0) {
                if (!p.getPlayedCombinations().get(entryKey)) {
                    p.getPlayedCombinations().replace(entryKey, false, true);
                    p.setScore(p.getScore() + entryValue);

                    gameUtils.printRound(p,
                            round,
                            oldScore,
                            entryValue,
                            Combinations.getEnumWithIndex(i).getLabel());
                    return;
                }
            } else
                break;
        }
        gameUtils.printRound(p, round, oldScore, 0, "No Combination");
    }

    //checking for combinations

    private int checkPair(List<Integer> diceRolled) {
        int maxScore = 0;

        for (int i = 0, j = 1; j < diceRolled.size(); i++, j++) {
            if (diceRolled.get(i).compareTo(diceRolled.get(j)) == 0) {
                if (Combinations.calcuatePair(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.calcuatePair(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkDoublePair(List<Integer> diceRolled) {
        int pair1 = 0, pair2;
        int maxScore = 0;

        for (int i = 0, j = 1; j < diceRolled.size(); i++, j++) {
            if (diceRolled.get(i).compareTo(diceRolled.get(j)) == 0) {
                if (pair1 == 0) {
                    pair1 = diceRolled.get(i);
                    i = ++i;
                    j = ++j;
                    continue;
                }
                pair2 = diceRolled.get(i);
                if (pair1 > 0
                        && pair2 > 0
                        && Combinations.calcuateDoublePair(pair1, pair2) > maxScore)
                    maxScore = Combinations.calcuateDoublePair(pair1, pair2);
            }
        }
        return maxScore;
    }

    private int checkTriple(List<Integer> diceRolled) {
        int maxScore = 0;

        for (int i = 0, j = 1, k = 2; k < diceRolled.size(); i++, j++, k++) {
            if (diceRolled.get(i).compareTo(diceRolled.get(j)) == 0
                    && diceRolled.get(k).compareTo(diceRolled.get(j)) == 0) {
                if (Combinations.calcuateTriple(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.calcuateTriple(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkFullHouse(List<Integer> diceRolled) {
        int maxScore = 0;
        if (diceRolled.get(0).compareTo(diceRolled.get(1)) == 0
                && (diceRolled.get(2).compareTo(diceRolled.get(3)) == 0
                && diceRolled.get(3).compareTo(diceRolled.get(4)) == 0))
            maxScore = Combinations.calcuateFullHouse(diceRolled.get(0), diceRolled.get(4));

        if (diceRolled.get(3).compareTo(diceRolled.get(4)) == 0
                && (diceRolled.get(0).compareTo(diceRolled.get(1)) == 0
                && diceRolled.get(1).compareTo(diceRolled.get(2)) == 0))
            maxScore = Combinations.calcuateFullHouse(diceRolled.get(4), diceRolled.get(0));

        return maxScore;
    }

    private int checkStraight(List<Integer> diceRolled) {
        int i = 1;
        int j = 0;
        if (diceRolled.get(0) == 2) {
            j = 1;
            i = 2;
        }
        for (; i < diceRolled.size(); i++) {
            if (!(diceRolled.get(i) == (i + 1 + j)))
                return 0;
        }
        return Combinations.calcuateStraight(diceRolled.get(4) == 5);
    }

    private int checkFourOfaKind(List<Integer> diceRolled) {
        if (diceRolled.get(0).compareTo(diceRolled.get(3)) == 0)
            return Combinations.calcuateFourOfAKind(diceRolled.get(0));
        if (diceRolled.get(1).compareTo(diceRolled.get(4)) == 0)
            return Combinations.calcuateFourOfAKind(diceRolled.get(1));
        return 0;
    }

    private int checkGenerala(List<Integer> diceRolled) {
        for (int i = 1; i < diceRolled.size(); i++) {
            if (!(diceRolled.get(i).compareTo(diceRolled.get(0)) == 0))
                return 0;
        }
        return Combinations.calcuateGenerala(diceRolled.get(0));
    }
}
