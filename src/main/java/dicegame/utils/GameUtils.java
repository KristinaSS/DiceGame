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

public final class GameUtils {
    private static Properties prop;


    private static Path path = Paths.get(CommonConstants.PATH);

    private GameUtils() {
    }

    // Utils

    public static List<Player> fillPlayerList(final int playerNum) {
        final List<Player> playerList = new ArrayList<>();
        for (int i = playerNum; i > 0; i--) {
            playerList.add(new Player(playerList.size() + 1)); //NOPMD
        }
        return playerList;
    }

    public static Properties readPropertiesFile() throws IOException {
        if (prop == null) {
            try (InputStream input = Files.newInputStream(path)) {
                prop = new Properties();
                prop.load(input);
            }
        }
        return prop;
    }

    public static String getPropertyFromPropFile(final String propertyStr)
            throws IOException {
        return readPropertiesFile()
                        .getProperty(propertyStr);
    }
}
