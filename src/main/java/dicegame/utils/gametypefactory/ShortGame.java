package dicegame.utils.gametypefactory;

import dicegame.application.Game;

public final class ShortGame implements GameType {
    ShortGame() { //NOPMD
    }

    @Override
    public Game buildGame() {
        return new Game.Builder()
                .setRounds(SHORT_GAME_ROUNDS)
                .setPlayerList(SHORT_GAME_PLAYER)
                .build();
    }
}
