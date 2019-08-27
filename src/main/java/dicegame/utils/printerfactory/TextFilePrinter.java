package dicegame.utils.printerfactory;

import dicegame.constants.CommonConstants;
import dicegame.elements.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public final class TextFilePrinter implements Printer {

    TextFilePrinter() { //NOPMD
    }

    @Override
    public void printEndGamePlayerStats(final List<Player> playerList)
            throws IOException {
        int placeInGame = 1;
        PrintWriter out = null;
        try {
            out = new PrintWriter(CommonConstants.TEXT_FILE_NAME);
            out.println(">>>  RESULTS  <<<<");
            out.println("Place       Player       Score");
            for (final Player player : playerList) {
                out.println(placeInGame + ".         Player "
                        + player.getPlayerNumber() + "   ->  "
                        + player.getScore());
                placeInGame++;
            }
        } finally {
            assert out != null;
            out.close();
        }
    }
}
