package dicegame;

import dicegame.application.Game;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameUtils {
    private GameUtils() {
    }

    //Utils
    public static void sortByValue() {
        List<Map.Entry<CombinationEnum, Integer>> list =
                new LinkedList<>(Dice.sortedScore.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        Dice.sortedScore.clear();

        HashMap<CombinationEnum, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<CombinationEnum, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        Dice.sortedScore = temp;
    }

    public static void endGame(List<Player> playerList, Player playerWithGenerala) {
        int previousScore = -1;
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");

        int placeInGame = 1;
        playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        if (!(playerWithGenerala == null)) {
            System.out.println("GENERALA! Player " + playerWithGenerala.getPlayerNumber());
            playerList.remove(playerWithGenerala);
            System.out.println(placeInGame + ".         Player " + playerWithGenerala.getPlayerNumber() + "   ->  " + playerWithGenerala.getScore());
        }

        for (Player p : playerList) {
            if (previousScore > p.getScore() || previousScore == -1)
                placeInGame++;
            if(playerWithGenerala == null && previousScore == -1)
                placeInGame--;
            System.out.println(placeInGame + ".         Player " + p.getPlayerNumber() + "   ->  " + p.getScore());
            previousScore = p.getScore();
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public static void printRound(Player player,
                           int round,
                           int oldScore,
                           int rolledScore,
                           String typeOfCombination) {
        Dice.sortDiceNaturalOrder(Dice.diceRolled);

        StringBuilder rolledDice = new StringBuilder(Dice.diceRolled.toString());
        rolledDice.deleteCharAt(0);
        rolledDice.deleteCharAt(rolledDice.length()-1);

        System.out.println(">>> round: " + round);
        System.out.println(">player " + player.getPlayerNumber() + ":");
        System.out.println("current score: " + oldScore);
        System.out.println("dice roll:" + rolledDice + "-> " + typeOfCombination + " (" + rolledScore + ") ");
        System.out.println("new score: " + player.getScore());
        System.out.println();
    }

    public static List<Player> fillPlayerList(int playerNum) {
        List<Player> playerList = new ArrayList<>();
        while (playerNum-- > 0) {
            playerList.add(new Player(playerList.size() + 1));
        }
        return playerList;
    }

    public static void readPropertiesFile(Path path){
        try(InputStream input = Files.newInputStream(path)){
            Properties prop = new Properties();
            prop.load(input);

            Game.getInstance().setRounds(Integer.parseInt(prop.getProperty("roundCount")));
            Game.getInstance().setPlayerCount(Integer.parseInt(prop.getProperty("playerCount")));

            Dice.numberOfDice = Integer.parseInt(prop.getProperty("diceCount"));
            Dice.numberOfSides = Integer.parseInt(prop.getProperty("numberOfSidesOnDice"));

        } catch (IOException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}

