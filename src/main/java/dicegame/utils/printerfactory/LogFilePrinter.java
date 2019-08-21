package dicegame.utils.printerfactory;

import dicegame.elements.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LogFilePrinter implements Printer{
    private static final Logger log = LogManager.getLogger(LogFilePrinter.class);

    @Override
    public void printEndGamePlayerStats(List<Player> playerList) {
        int placeInGame = 1;

        log.info("NEW GAME");
        for (Player player : playerList) {
            log.info(placeInGame + ".         Player " + player.getPlayerNumber() + "   ->  " + player.getScore());
            placeInGame++;
        }
    }
}
