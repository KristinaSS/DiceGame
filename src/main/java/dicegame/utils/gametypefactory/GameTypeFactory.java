package dicegame.utils.gametypefactory;

public final class GameTypeFactory { //NOPMD

    public GameType getGameType(final String gameType) {
        switch (gameType.toUpperCase()
                        .trim()) {
            case "CUSTOM":
                return new CustomGame(); //NOPMD
            case "SHORT":
                return new ShortGame(); //NOPMD
            case "LONG":
                return new LongGame(); //NOPMD
            default:
                return null;
        }
    }
}
