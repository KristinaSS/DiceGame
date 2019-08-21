package dicegame.utils.gametypefactory;

import dicegame.application.Game;

public class LongGame implements GameType{

    @Override
    public Game buildGame() {
        return new Game.Builder()
                .setRounds(LONG_GAME_ROUNDS)
                .setPlayerList(LONG_GAME_PLAYER_COUNT)
                .build();
    }
}
