package dicegame.utils.gametypefactory;

public class GameTypeFactory {
    public GameType getGameType (String gameType){
        switch (gameType.toUpperCase().trim()){
            case "CUSTOM":
                return new CustomGame();
            case "SHORT" :
                return new ShortGame();
            case "LONG" :
                return new LongGame();
                default:
                    return null;
        }
    }
}
