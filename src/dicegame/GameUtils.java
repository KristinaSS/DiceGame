package dicegame;

import dicegame.application.Game;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.util.*;

public class GameUtils {
    private GameUtils() {
    }

    //Utils

    public static void printEndGamePlayerStats(int placeInGame,Player player){
        System.out.println(placeInGame + ".         Player "
                + player.getPlayerNumber() + "   ->  "
                + player.getScore());

    }

    public static void printRound(Player player,
                           int round,
                           int oldScore,
                           int rolledScore,
                           String typeOfCombination) {

        System.out.println(">>> round: " + round);
        System.out.println(">player " + player.getPlayerNumber() + ":");
        System.out.println("current score: " + oldScore);
        System.out.println("dice roll:" + Dice.getDiceRolledStr() + "-> " + typeOfCombination + " (" + rolledScore + ") ");
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

    public static int findFirstValueGreaterThanOrEqualTo(int compareBy) {
        for (Map.Entry<Integer, Integer> entry : Dice.getTimesRepeatedEachDieSideTreeMap().entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey();
            }
        }
        return -1;
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

