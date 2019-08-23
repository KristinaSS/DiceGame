package dicegame.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dicegame.constants.CommonConstants;
import dicegame.elements.Player;

public class GameUtils {
    private static Properties prop;

    private static Path path = Paths.get(CommonConstants.PATH);

    private GameUtils() {
    }

    // Utils

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

    public static String getPropertyFromPropFile(String propertyStr){
        String property = GameUtils.readPropertiesFile().getProperty(propertyStr);
        /*if (count < 1)
            throw new IllegalCountException("Your " + property + " must be larger than 0");*/
        return property;
    }

}
