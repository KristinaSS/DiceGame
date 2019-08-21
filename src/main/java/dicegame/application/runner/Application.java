package dicegame.application.runner;

import dicegame.application.Game;
import dicegame.exceptions.IllegalCountException;
import dicegame.exceptions.IllegalGameTypeException;
import dicegame.exceptions.IllegalPrinterTypeException;
import dicegame.utils.GameUtils;
import dicegame.utils.gametypefactory.GameType;
import dicegame.utils.gametypefactory.GameTypeFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Application {
    private static final Logger log = LogManager.getLogger(Application.class);
        public static long start;


    public static void main(String[] args) {
        start = System.nanoTime();
        String gameTypeStr = GameUtils.getPropertyFromPropFile("gametype");

        GameType gameType = new GameTypeFactory().getGameType(gameTypeStr);

        try{
            if(gameType != null) {
                Game diceGame =gameType.buildGame();

                // game is ready to be played
                diceGame.playGame();
            }else
                throw new IllegalGameTypeException("There is no such game: " + gameTypeStr);

        }catch (IllegalGameTypeException e){
            log.error( "An IllegalGameTypeException was thrown :" + e.getMessage());
        }catch (IllegalPrinterTypeException e){
            log.error("An IllegalPrinterTypeException was thrown :"+ e.getMessage());
        }catch (IllegalCountException e){
            log.error("An IllegalCountException was thrown! :" + e.getMessage());
        }
    }
}
