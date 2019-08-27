package dicegame.utils.gametypefactory;

import dicegame.application.Game;

import java.io.IOException;

public interface GameType {
    int SHORT_GAME_ROUNDS = 10;
    int LONG_GAME_ROUNDS = 50;

    int SHORT_GAME_PLAYER = 8;
    int LONG_GAME_PLAYER = 20;


    Game buildGame() throws IOException;
}
