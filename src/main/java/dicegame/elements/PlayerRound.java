package dicegame.elements;

import static dicegame.utils.FindCombinationUtil.*; //NOPMD

import dicegame.constants.CombinationEnum;
import dicegame.exceptions.LoggerLevelNotEnabledException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//todo javadoc
public final class PlayerRound {
    private final int round;

    private final Player player;

    private int maxRoundScore;

    private CombinationEnum comboForMaxScore;

    /**
     * This is a private Logger constant make from Logger class from Log4j.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(PlayerRound.class);

    public PlayerRound(final Player curPlayer,
                       final int curRound) {
        this.round = curRound;
        this.player = curPlayer;
        this.maxRoundScore = 0;
        this.comboForMaxScore = null;
    }

    //public methods

    public void playPlayerRound() throws LoggerLevelNotEnabledException {
        final int oldScore = player.getScore();

        player.rollDice();

        callMethodsToUpdateScore();

        if (maxRoundScore > 0) {
            player.getPlayedComboSet()
                  .add(comboForMaxScore);
            player.setScore(player.getScore() + maxRoundScore);
            printRound(oldScore);
            return;
        }
        printRound(oldScore);
    }

    //caller methods

    private void callMethodsToUpdateScore() {

        setPlayer(player);

        updateScoreIfGenerala();

        if (player.getPlayedComboSet()
                  .size() >= CombinationEnum.values().length - 1
                || maxRoundScore > 0) {
            return;
        }

        updateScoreIfStraight();

        final int fourOfAKind = getFourOfAKind();
        final int triple = getTriple(fourOfAKind);
        final int pair = getPair(triple);
        final int secondPair = getSecondPair(pair);

        updateScoreIfFourOfAKind(fourOfAKind);

        updateRoundScoreIfTripleValid(triple);

        updateRoundScoreIfFullHouseValid(triple, pair);

        updateRoundScoreIfPairValid(triple, pair);

        updateRoundScoreIfDoublePairValid(pair, secondPair);

        comboForMaxScore = getComboForMaxScore();
        maxRoundScore = getMaxRoundScore();
    }

    private void printRound(final int oldScore)
            throws LoggerLevelNotEnabledException {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(">>> round: " + round);
            LOGGER.info(">player " + player.getPlayerNumber() + ":");
            LOGGER.info("current score: " + oldScore);
            LOGGER.info("dice roll:" + DiceRolled.rolledDiceListToString()
                    + "-> " + (comboForMaxScore == null
                    ? "No combination"
                    : comboForMaxScore.getLabel())
                    + " (" + maxRoundScore + ") ");
            LOGGER.info("new score: " + player.getScore());
            LOGGER.info(System.lineSeparator());
        } else {
            throw new LoggerLevelNotEnabledException("Info logger level "
                    + "not enabled!");
        }
    }

}
