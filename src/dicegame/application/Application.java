package dicegame.application;

import dicegame.exceptions.IllegalGameTypeException;
import dicegame.utils.gametypefactory.GameType;
import dicegame.utils.gametypefactory.GameTypeFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
    public static final Logger LOGGER = Logger.getAnonymousLogger();
    static long start;

    public static void main(String[] args) {
        start = System.nanoTime();
        String gameTypeStr = "custom";

        GameType gameType = new GameTypeFactory().getGameType(gameTypeStr);

        try{
            if(gameType == null){
                throw new IllegalGameTypeException("There is no such game: " + gameTypeStr);
            }
        }catch (IllegalGameTypeException e){
            LOGGER.log(Level.SEVERE, "An IllegalGameTypeException was thrown", e);
            System.exit(-1 );
        }

        Game diceGame =gameType.buildGame();

        // game is ready to be played
        diceGame.playGame();
    }
}
