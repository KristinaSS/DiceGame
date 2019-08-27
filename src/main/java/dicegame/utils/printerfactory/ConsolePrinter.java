package dicegame.utils.printerfactory;

import dicegame.elements.Player;
import dicegame.exceptions.LoggerLevelNotEnabledException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class ConsolePrinter implements Printer { //NOPMD
    /**
     * This is a private Logger constant make from Logger class from Log4j.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ConsolePrinter.class);


    @Override
    public void printEndGamePlayerStats(final List<Player> playerList)
            throws LoggerLevelNotEnabledException {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(">>>  RESULTS  <<<<");
            LOGGER.info("Place       Player       Score");
            int placeInGame = 1;
            for (final Player player : playerList) {
                LOGGER.info(placeInGame + ".         Player "
                        + player.getPlayerNumber() + "   ->  "
                        + player.getScore());
                placeInGame++;
            }
        } else {
            throw new LoggerLevelNotEnabledException("Info Level Not enabled!");
        }
    }
}
