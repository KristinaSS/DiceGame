package dicegame.application;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Application {
    private static int roundCount;
    private static int playerCount;

    public static void main(String[] args) {
        readPropertiesFile(Paths.get("src/dicegame/resources/config.properties"));

        Game diceGame = new Game(roundCount,playerCount);
        diceGame.playGame();
    }

    private static void readPropertiesFile(Path path){

        try(InputStream input = Files.newInputStream(path)){
            Properties prop = new Properties();
            prop.load(input);

            roundCount = Integer.parseInt(prop.getProperty("roundCount"));
            playerCount = Integer.parseInt(prop.getProperty("playerCount"));


        } catch (IOException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}
