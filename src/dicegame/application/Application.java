package dicegame.application;

import dicegame.GameUtils;

import java.nio.file.Paths;

public class Application {

    public static void main(String[] args) {
        try {
            Game diceGame = Game.getInstance();
            GameUtils.getInstance().readPropertiesFile(Paths
                    .get("C:/Users/kristina.stoyanova/IdeaProjects/DiceGame/src/resources/config.properties"));

            //game is ready to be played
            diceGame.playGame();
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
