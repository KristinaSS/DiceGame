package dicegame.utils.gametypefactory;

import dicegame.application.Game;
import dicegame.utils.GameUtils;

public interface GameType {
    int SHORT_GAME_ROUNDS = 10;
    int LONG_GAME_ROUNDS = 50;
    int CUSTOM_GAME_ROUNDS = GameUtils.getRoundCountFromProp();

    int SHORT_GAME_PLAYER_COUNT = 8;
    int LONG_GAME_PLAYER_COUNT = 20;
    int CUSTOM_GAME_PLAYER_COUNT = GameUtils.getPlayerCountFromProp();


    Game buildGame();
}
