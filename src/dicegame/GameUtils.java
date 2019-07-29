package dicegame;

import dicegame.elements.Player;

import java.util.*;

public class GameUtils {
    private static GameUtils gameUtilsInstance = null;

    public static GameUtils getInstance(){
        if(gameUtilsInstance==null)
            gameUtilsInstance = new GameUtils();
        return gameUtilsInstance;
    }

    protected GameUtils() {
    }

    public Map.Entry<Integer,Integer> getEntry(int i) {
        int j = 0;
        for(Map.Entry<Integer, Integer>entry : Player.getSortScore().entrySet())
            if(j++ == i)return entry;

        return null;
    }

    public HashMap<Integer, Integer> sortByValue() {
        List<Map.Entry<Integer, Integer> > list =
                new LinkedList<>(Player.getSortScore().entrySet());

        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void endGame(List<Player> playerList, Player winner){
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");
        int i = 1;
        playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        if(!(winner ==null)){
            playerList.remove(winner);
            playerList.add(0,winner);
        }

        for(Player p: playerList){
            System.out.println(i+".         Player "+p.getPlayerNumber()+ "   ->  " + p.getScore());
            i++;
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void printRound(Player p, int round, int oldScore, int rolledScore, String typeOfCombination){
        System.out.println(">>> round: "+ round);
        System.out.println(">player "+ p.getPlayerNumber()+":");
        System.out.println("current score: "+oldScore);
        System.out.println("dice roll: "+p.getDiceRolled() + " -> " + typeOfCombination+" ("+ rolledScore+") ");
        System.out.println("new score: "+p.getScore());
        System.out.println();
    }

    public List<Player> fillPlayerList(int playerNum){
        List<Player> playerList = new ArrayList<>();
        while(playerNum-->0){
            playerList.add(new Player(playerList.size()+1));
        }
        return playerList;
    }
}
