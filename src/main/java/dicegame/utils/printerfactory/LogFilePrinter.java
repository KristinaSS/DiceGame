package dicegame.utils.printerfactory;

import dicegame.elements.Player;
import dicegame.exceptions.LoggerLevelNotEnabledException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class LogFilePrinter implements Printer { //NOPMD
    private static final Logger LOGGER
            = LogManager.getLogger(LogFilePrinter.class);


    @Override
    public void printEndGamePlayerStats(final List<Player> playerList)
            throws LoggerLevelNotEnabledException {
        int placeInGame = 1;

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(System.lineSeparator());
            LOGGER.info("NEW GAME");
            for (final Player player : playerList) {
                LOGGER.info(placeInGame + ".         Player "
                        + player.getPlayerNumber() + "   ->  "
                        + player.getScore());
                placeInGame++;
            }
        } else {
            throw new LoggerLevelNotEnabledException("Info level not enabled");
        }
    }
}
