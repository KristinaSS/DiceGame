package dicegame.utils.gametypefactory;

import dicegame.application.Game;

public class CustomGame implements GameType{
    @Override
    public Game buildGame() {
        return new Game.Builder()
                .setRounds(CUSTOM_GAME_ROUNDS)
                .setPlayerList(CUSTOM_GAME_PLAYER_COUNT)
                .build();
    }
}
