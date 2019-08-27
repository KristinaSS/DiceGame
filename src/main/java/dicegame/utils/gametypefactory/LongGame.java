package dicegame.utils.gametypefactory;

import dicegame.application.Game;

public final class LongGame implements GameType {

    LongGame() { //NOPMD
    }

    @Override
    public Game buildGame() {
        return new Game.Builder()
                .setRounds(LONG_GAME_ROUNDS)
                .setPlayerList(LONG_GAME_PLAYER)
                .build();
    }
}
