package dicegame.application;

import dicegame.elements.DiceRolled;
import dicegame.exceptions.IllegalCountException;
import dicegame.exceptions.IllegalGameTypeException;
import dicegame.exceptions.IllegalPrinterTypeException;
import dicegame.exceptions.LoggerLevelNotEnabledException;
import dicegame.utils.GameUtils;
import dicegame.utils.gametypefactory.GameType;
import dicegame.utils.gametypefactory.GameTypeFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public final class Application {

    /**
     * This is a private Logger constant make from Logger class from Log4j.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(Application.class);

    /**
     * This isn't part of the task it is for my own experiments.
     * Dont pay attention to this.
     */
    //private static long start;
    private Application() {
    }

    /**
     * The runner method. Used to create the game and run the program.
     *
     * @param args They are not uses in this project;
     */
    public static void main(final String[] args) {
        //start = System.nanoTime();
        try {
            DiceRolled.Die.setNumberOfSides();

            final String gameTypeStr = GameUtils
                    .getPropertyFromPropFile("gametype");

            final GameType gameType = new GameTypeFactory()
                    .getGameType(gameTypeStr);

            if (gameType == null) {
                throw new IllegalGameTypeException("There is no such game: "
                        + gameTypeStr);
            } else {
                final Game diceGame = gameType.buildGame();

                // game is ready to be played
                diceGame.playGame();
            }

        } catch (IllegalGameTypeException
                | IllegalPrinterTypeException
                | IllegalCountException
                | IOException
               /* | LoggerLevelNotEnabledException*/ e) {
            checkLoggerEnabled(e);
        } catch (LoggerLevelNotEnabledException e)
        {
            e.printStackTrace();
        }
    }

    private static void checkLoggerEnabled(final Exception exception) { //NOPMD
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("An Exception was thrown :"
                    + exception.getMessage());
        } else {
            exception.printStackTrace(); //NOPMD//NOPMD
            try {
                throw new LoggerLevelNotEnabledException("An "
                        + "Exception was thrown :"
                        + exception.getMessage());
            } catch (LoggerLevelNotEnabledException ex) {
                ex.printStackTrace(); //NOPMD
            }
        }
    /*static long getStart() {
        return start;
    }*/
    }
}
