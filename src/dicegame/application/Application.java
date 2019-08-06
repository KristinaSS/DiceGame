package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.PathEnum;

import java.nio.file.Paths;

public class Application {

    public static void main(String[] args){
       long start = System.nanoTime();

       Game diceGame = Game.getInstance();
       GameUtils.readPropertiesFile(Paths
               .get(PathEnum.KRISTINA_WORK_PATH.getFilePathStr()));

            //game is ready to be played
        diceGame.playGame();

        long end = System.nanoTime();
        System.out.println("Took: " + ((end - start) / 1000000) + " ms");
        System.out.println("Took: " + (end - start)/ 1000000000 + " seconds");
    }
}
