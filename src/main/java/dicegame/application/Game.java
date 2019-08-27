package dicegame.application;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import dicegame.elements.DiceRolled;
import dicegame.exceptions.IllegalCountException;
import dicegame.exceptions.IllegalPrinterTypeException;
import dicegame.elements.Player;
import dicegame.elements.PlayerRound;
import dicegame.constants.CombinationEnum;
import dicegame.exceptions.LoggerLevelNotEnabledException;
import dicegame.utils.printerfactory.Printer;
import dicegame.utils.printerfactory.PrinterFactory;
import dicegame.utils.GameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class Game { //NOPMD

    /**
     * This is a private Logger constant make from Logger class from Log4j.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(Game.class);
    /**
     * This is a Game class member that holds the number of rounds this game
     * will have.
     */
    private int rounds;

    /**
     * This is a Game class member that holds a List of Player Objects (refs do
     * not point  towards null). This List has been or will be initialized at
     * some point in the beginning of the application before the game starts
     */
    private List<Player> playerList;

    //Methods
    private Game() {
    }

    //play or end Game
    void playGame() throws LoggerLevelNotEnabledException, IOException { //NOPMD
        DiceRolled.setNumberOfDice();

        PlayerRound playerRound;

        LOGGER.info(">>> WELCOME TO THE DICE GAME <<<");

        for (int curRound = 1; curRound <= rounds; curRound++) {
            for (final Player player : playerList) {

                playerRound = new PlayerRound(player, curRound); //NOPMD

                playerRound.playPlayerRound();

                if (player.getPlayedComboSet()
                          .contains(CombinationEnum.GENERALA)) {
                    endGame();
                }
            }
        }
        endGame();
    }

    @SuppressWarnings("checkstyle:magicnumber")
    private void endGame() throws LoggerLevelNotEnabledException, IOException {
        Collections.sort(playerList);
        final String printerTypeStr = GameUtils
                .getPropertyFromPropFile("printerType");

        final Printer printer = new PrinterFactory().getPrinter(printerTypeStr);

        if (printer == null) {
            throw new IllegalPrinterTypeException("No such printer type: "
                    + printerTypeStr);
        } else {
            printer.printEndGamePlayerStats(playerList);
        }
        System.exit(0);
    }

    public static class Builder {
        /**
         * Builder class version of playerList, it uses it in the build
         * method to build a Game Object.
         */
        private List<Player> playerList;
        /**
         * Builder class version of rounds, it uses it in the build
         * method to build a Game Object.
         */
        private int rounds;

        /**
         * Uses playerCount parm to fill the builder class playerList with the
         * given playerCount and sets the created List of Player Objects. Also
         * checks if the given number of players is valid. Throws an exception
         * if not.
         *
         * @param playerCount the size of the playerList
         * @return an instance of this Builder Class
         */
        public Builder setPlayerList(final int playerCount) { //NOPMD
            if (playerCount < 1) {
                throw new IllegalCountException("Your player count"
                        + " needs to be higher than 0");
            }

            this.playerList = GameUtils.fillPlayerList(playerCount);

            return this;
        }

        /**
         * Uses roundCount parm to set the Builder Class member rounds. Also
         * checks if the given number of rounds is valid. Throws an exception
         * if not.
         *
         * @param roundsCount the number of rounds to be set for the Game object
         *                    when built.
         * @return an instance of this Builder class
         */
        public Builder setRounds(final int roundsCount) { //NOPMD
            if (roundsCount < 1) {
                throw new IllegalCountException("Your round count "
                        + "needs to be higher than 0");
            }
            this.rounds = roundsCount;
            return this;
        }

        /**
         * This methods is the core method of the Builder design pattern. It
         * "builds" an instance of Game with all the necessary fields needed.
         *
         * @return a completed Game instance with all the Game Class member
         * fields needed for the class to function without error.
         */
        public Game build() {
            final Game game = new Game();
            game.playerList = this.playerList;
            game.rounds = this.rounds;

            return game;
        }
    }
}
