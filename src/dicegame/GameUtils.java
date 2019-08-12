package dicegame;

import dicegame.application.Game;
import dicegame.elements.DiceRolled;
import dicegame.elements.Die;
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

    public static void printEndGamePlayerStats(int placeInGame, int playerNumber, int playerScore) {
        System.out.println(placeInGame + ".         Player "
                + playerNumber + "   ->  "
                + playerScore);

    }

    public static List<Player> fillPlayerList(int playerNum) {
        List<Player> playerList = new ArrayList<>();
        while (playerNum-- > 0) {
            playerList.add(new Player(playerList.size() + 1));
        }
        return playerList;
    }

    public static void readPropertiesFile(Path path) {
        try (InputStream input = Files.newInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);

            Game.getInstance().setRounds(Integer.parseInt(prop.getProperty("roundCount")));
            Game.getInstance().setPlayerCount(Integer.parseInt(prop.getProperty("playerCount")));

            DiceRolled.numberOfDice = Integer.parseInt(prop.getProperty("diceCount"));
            Die.numberOfSides = Integer.parseInt(prop.getProperty("numberOfSidesOnDice"));

        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

}

