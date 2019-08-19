package dicegame.application;

import dicegame.exceptions.IllegalCountException;
import dicegame.exceptions.IllegalGameTypeException;
import dicegame.exceptions.IllegalPrinterTypeException;
import dicegame.utils.gametypefactory.GameType;
import dicegame.utils.gametypefactory.GameTypeFactory;
import org.apache.log4j.Logger;

public class Application {
    //private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    private static Logger log = Logger.getRootLogger();
        static long start;


    public static void main(String[] args) {
        start = System.nanoTime();
        String gameTypeStr = "custom";

        GameType gameType = new GameTypeFactory().getGameType(gameTypeStr);

        try{
            if(gameType != null) {
                Game diceGame =gameType.buildGame();

                // game is ready to be played
                diceGame.playGame();
            }else
                throw new IllegalGameTypeException("There is no such game: " + gameTypeStr);

        }catch (IllegalGameTypeException e){
            //LOGGER.log(Level.SEVERE, "An IllegalGameTypeException was thrown", e);
            log.debug( "An IllegalGameTypeException was thrown");
        }catch (IllegalPrinterTypeException e){
            //Application.log.log(Level.WARNING, "An IllegalPrinterTypeException was thrown", e);
            log.debug("An IllegalPrinterTypeException was thrown");
        }catch (IllegalCountException e){
            //Application.log.log(Level.SEVERE,"An IllegalCountException was thrown!",e);
            log.debug("An IllegalCountException was thrown!");
        }
    }
}
