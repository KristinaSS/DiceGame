package dicegame;

import dicegame.elements.CombinationEnum;
import dicegame.elements.Player;

import java.util.*;

public class GameUtils {
    private static GameUtils gameUtilsInstance = null;

    public static GameUtils getInstance() {
        if (gameUtilsInstance == null)
            gameUtilsInstance = new GameUtils();
        return gameUtilsInstance;
    }

    protected GameUtils() {
    }

    //Utils

    public Map.Entry<CombinationEnum, Integer> getEntry(int i) {
        int j = 0;
        for (Map.Entry<CombinationEnum, Integer> entry : Player.getSortScore().entrySet()) {
            if (j++ == i)
                return entry;
        }
        return null;
    }

    public HashMap<CombinationEnum, Integer> sortByValue() {

        List<Map.Entry<CombinationEnum, Integer>> list =
                new LinkedList<>(Player.getSortScore().entrySet());

        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<CombinationEnum, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<CombinationEnum, Integer> aa : list)
            temp.put(aa.getKey(), aa.getValue());
        return temp;
    }

    public void endGame(List<Player> playerList, Player winner) {
        int previousScore = -1;
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");
        int i = 1;
        playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        if (!(winner == null)) {
            System.out.println("GENERALA! Player " + winner.getPlayerNumber());
            playerList.remove(winner);
            System.out.println(i + ".         Player " + winner.getPlayerNumber() + "   ->  " + winner.getScore());
        }

        for (Player p : playerList) {
            if (previousScore > p.getScore() || previousScore == -1)
                i++;
            System.out.println(i + ".         Player " + p.getPlayerNumber() + "   ->  " + p.getScore());
            previousScore = p.getScore();
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void printRound(Player p,
                           int round,
                           int oldScore,
                           int rolledScore,
                           String typeOfCombination) {
        System.out.println(">>> round: " + round);
        System.out.println(">player " + p.getPlayerNumber() + ":");
        System.out.println("current score: " + oldScore);
        System.out.println("dice roll: " + p.getDiceRolled() + " -> " + typeOfCombination + " (" + rolledScore + ") ");
        System.out.println("new score: " + p.getScore());
        System.out.println();
    }

    public List<Player> fillPlayerList(int playerNum) {
        List<Player> playerList = new ArrayList<>();
        while (playerNum-- > 0) {
            playerList.add(new Player(playerList.size() + 1));
        }
        return playerList;
    }
}

