package dicegame.utils;

import dicegame.constants.CommonConstants;
import dicegame.elements.Player;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class TextFilePrinter implements Printer {

    @Override
    public void printEndGamePlayerStats(List<Player> playerList) {
        int placeInGame = 1;
        PrintWriter out = null;
        try {
            out = new PrintWriter(CommonConstants.TEXT_FILE_NAME);
            out.println(">>>  RESULTS  <<<<");
            out.println("Place       Player       Score");
            for (Player player : playerList) {
                out.println(placeInGame + ".         Player " + player.getPlayerNumber() + "   ->  " + player.getScore());
                placeInGame++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            out.close();
        }
    }
}
