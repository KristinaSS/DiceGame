package dicegame.utils;

import dicegame.constants.CombinationEnum;
import dicegame.elements.DiceRolled;
import dicegame.elements.Player;

import java.util.Map;

import static dicegame.elements.DiceRolled.findFirstValueGreaterThanOrEqualTo;

public final class FindCombinationUtil {
    private static CombinationEnum comboForMaxScore;
    private static int maxRoundScore;
    private static Player player;

    private FindCombinationUtil() {
    }

    public static CombinationEnum getComboForMaxScore() {
        return comboForMaxScore;
    }

    public static int getMaxRoundScore() {
        return maxRoundScore;
    }

    public static void setPlayer(final Player curPlayer) {
        FindCombinationUtil.player = curPlayer;
        maxRoundScore = 0;
    }

    //Updating score and getting combo die numbers

    public static void updateScoreIfGenerala() {
        if (DiceRolled.getDiceRepeated()
                      .size() != 1) {
            return;
        }

        final CombinationEnum generala = CombinationEnum.GENERALA;
        if (!player.getPlayedComboSet()
                   .contains(generala)) {
            comboForMaxScore = generala;
            maxRoundScore
                    = generala
                    .calculateCombination(findFirstValueGreaterThanOrEqualTo(
                            generala.getDiceCount()));
        }
    }

    public static void updateScoreIfStraight() {
        final Map<Integer, Integer> diceRepeated
                = DiceRolled.getDiceRepeated();
        final CombinationEnum straight = CombinationEnum.STRAIGHT;
        //if mapsize == 5
        if (diceRepeated.size() < straight.getDiceCount()) {
            return; //NOPMD
        }

        //if player has already played straight
        if (player.getPlayedComboSet()
                  .contains(straight)) {
            return;
        }

        int goBackPosition;
        int straightCounter = 0;
        int begStraight = 0;
        final int numberOfSides = DiceRolled.Die.getNumberOfSides();
        for (goBackPosition = 0;
                goBackPosition < numberOfSides;
                goBackPosition++) {
            if (diceRepeated.containsKey(numberOfSides - goBackPosition)) {
                if (straightCounter == 0) {
                    begStraight
                            = numberOfSides - goBackPosition;
                }
                straightCounter++;
            } else {
                straightCounter = 0;
            }
            if (straightCounter == straight.getDiceCount()) {
                updateRoundScore(straight
                        .calculateCombination(begStraight), straight);
            }
        }
    }

    public static void updateScoreIfFourOfAKind(final int foundFourOfAKind) {
        if (foundFourOfAKind <= 0) {
            return; //NOPMD
        }
        final CombinationEnum fourOfAKind = CombinationEnum.FOUR_OF_A_KIND;
        if (player.getPlayedComboSet()
                  .contains(fourOfAKind)) {
            return;
        }

        final int tempScore
                = fourOfAKind.calculateCombination(foundFourOfAKind);
        updateRoundScore(tempScore, fourOfAKind);
    }

    public static void updateRoundScoreIfTripleValid(final int tripleDie) {
        if (tripleDie <= 0) {
            return; //NOPMD
        }
        final CombinationEnum triple = CombinationEnum.TRIPLE;
        if (player.getPlayedComboSet()
                  .contains(triple)) {
            return;
        }

        final int tempScore = triple.calculateCombination(tripleDie);
        updateRoundScore(tempScore, triple);
    }

    public static void updateRoundScoreIfFullHouseValid(final int triple,
                                                        final int pair) {
        if (triple <= 0 || pair <= 0) {
            return; //NOPMD
        }
        final CombinationEnum fullHouse = CombinationEnum.FULL_HOUSE;
        if (player.getPlayedComboSet()
                  .contains(fullHouse)) {
            return;
        }

        final int tempScore = fullHouse
                .calculateCombination(CombinationEnum
                        .TRIPLE
                        .getDiceCount() * triple) + (CombinationEnum
                .PAIR
                .getDiceCount() * pair);
        updateRoundScore(tempScore, fullHouse);
    }

    public static void updateRoundScoreIfPairValid(final int tripleDie,
                                                   final int pairDie) {
        final CombinationEnum pair = CombinationEnum.PAIR;
        if (player.getPlayedComboSet()
                  .contains(pair)) {
            return;
        }
        int tempScore;
        if (tripleDie > 0 && tripleDie > pairDie) {
            tempScore = pair.calculateCombination(tripleDie);
            updateRoundScore(tempScore, pair);
        } else if (pairDie > 0) {
            tempScore = pair.calculateCombination(pairDie);
            updateRoundScore(tempScore, pair);
        }
    }

    public static void updateRoundScoreIfDoublePairValid(final int pair,
                                                         final int secondPair) {
        final CombinationEnum doublePair = CombinationEnum.DOUBLE_PAIR;
        if (secondPair > 0 && !(player.getPlayedComboSet()
                                      .contains(doublePair))) {
            final int tempScore = doublePair.calculateCombination(pair
                    + secondPair);
            updateRoundScore(tempScore, doublePair);
        }
    }

    public static int getFourOfAKind() {
        return findFirstValueGreaterThanOrEqualTo(CombinationEnum
                .FOUR_OF_A_KIND
                .getDiceCount());
    }

    public static int getTriple(final int fourOfAKind) {
        int triple = findFirstValueGreaterThanOrEqualTo(CombinationEnum
                .TRIPLE
                .getDiceCount());
        if (fourOfAKind > triple) {
            triple = fourOfAKind;
        }
        return triple;
    }

    public static int getPair(final int triple) {
        DiceRolled.getDiceRepeated()
                  .remove(triple);
        return findFirstValueGreaterThanOrEqualTo(CombinationEnum
                .PAIR
                .getDiceCount());
    }

    public static int getSecondPair(final int pair) {
        if (pair > 0) {
            DiceRolled.getDiceRepeated()
                      .remove(pair);
            return findFirstValueGreaterThanOrEqualTo(CombinationEnum//NOPMD
                    .DOUBLE_PAIR
                    .getDiceCount());
        }
        return -1;
    }

    private static void updateRoundScore(final int score,
                                         final CombinationEnum combo) {
        if (score > maxRoundScore) {
            maxRoundScore = score;
            comboForMaxScore = combo;
        }
    }
}
