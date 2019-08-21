package dicegame.utils.gametypefactory;

import dicegame.application.Game;
import dicegame.exceptions.IllegalCountException;

public class CustomGame implements GameType{
    @Override
    public Game buildGame() {
        if(CUSTOM_GAME_PLAYER_COUNT<1)
            throw new IllegalCountException("Your player count must be larger than 0");
        if(CUSTOM_GAME_ROUNDS<1)
            throw new IllegalCountException("Your round count must be larger than 0");

        return new Game.Builder()
                .setRounds(CUSTOM_GAME_ROUNDS)
                .setPlayerList(CUSTOM_GAME_PLAYER_COUNT)
                .build();
    }
}
