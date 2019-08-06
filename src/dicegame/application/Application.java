package dicegame.application;

import dicegame.GameUtils;

import java.nio.file.Paths;
import java.util.ConcurrentModificationException;

public class Application {

    public static void main(String[] args) throws ConcurrentModificationException {
       long start = System.nanoTime();

        try {
            Game diceGame = Game.getInstance();
            GameUtils.readPropertiesFile(Paths
                    .get("C:/Users/kristina.stoyanova/IdeaProjects/DiceGame/rescources/config.properties"));

            //game is ready to be played
            diceGame.playGame();
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        long end = System.nanoTime();
        System.out.println("Took: " + ((end - start) / 1000000) + " ms");
        System.out.println("Took: " + (end - start)/ 1000000000 + " seconds");
    }
}
