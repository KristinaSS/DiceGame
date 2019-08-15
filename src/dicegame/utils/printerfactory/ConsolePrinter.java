package dicegame.utils.printerfactory;

import dicegame.elements.Player;

import java.util.List;

public class ConsolePrinter implements Printer {
    @Override
    public void printEndGamePlayerStats(List<Player> playerList) {
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");
        int placeInGame = 1;
        for (Player player : playerList) {
            System.out.println(placeInGame + ".         Player " + player.getPlayerNumber() + "   ->  " + player.getScore());
            placeInGame++;
        }
    }
}
