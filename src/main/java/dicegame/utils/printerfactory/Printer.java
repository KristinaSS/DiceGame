package dicegame.utils.printerfactory;

import dicegame.elements.Player;
import dicegame.exceptions.LoggerLevelNotEnabledException;

import java.io.IOException;
import java.util.List;

public interface Printer {
    void printEndGamePlayerStats(List<Player> playerList)
            throws LoggerLevelNotEnabledException, IOException;
}
