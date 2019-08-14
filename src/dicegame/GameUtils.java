package dicegame;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dicegame.elements.Player;
import dicegame.constants.PathEnum;

public class GameUtils {
    private static Properties prop;

    private static Path path = Paths.get(PathEnum.KRISTINA_WORK_PATH.getFilePathStr());

    private GameUtils() {
    }

    // Utils

    public static void printEndGamePlayerStats(int placeInGame, int playerNumber, int playerScore) {
        System.out.println(placeInGame + ".         Player " + playerNumber + "   ->  " + playerScore);

    }

    public static List<Player> fillPlayerList(int playerNum) {
        List<Player> playerList = new ArrayList<>();
        while (playerNum-- > 0) {
            playerList.add(new Player(playerList.size() + 1));
        }
        return playerList;
    }

    public static Properties readPropertiesFile() {
        if (prop == null) {
            try (InputStream input = Files.newInputStream(path)) {
                prop = new Properties();
                prop.load(input);

            } catch (IOException exception) {
                exception.printStackTrace();
                System.exit(-1);
            }
        }

        return prop;
    }

}
