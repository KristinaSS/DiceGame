package dicegame.utils.gametypefactory;

import dicegame.application.Game;
import dicegame.constants.CommonConstants;
import dicegame.exceptions.IllegalCountException;
import dicegame.utils.GameUtils;

import java.io.IOException;

public final class CustomGame implements GameType {
    CustomGame() { //NOPMD
    }
    @Override
    public Game buildGame() throws IOException {
        final int cusGamePlayers = Integer.parseInt(GameUtils
                .getPropertyFromPropFile(CommonConstants.PLAYER_COUNT_STR));
        if (cusGamePlayers < 1) {
            throw new IllegalCountException("Your player count "
                    + "must be larger than 0");
        }

        final int cusGameRounds = Integer.parseInt(GameUtils
                .getPropertyFromPropFile(CommonConstants.ROUND_COUNT_STR));
        if (cusGameRounds < 1) {
            throw new IllegalCountException("Your round count "
                    + "must be larger than 0");
        }

        return new Game.Builder()
                .setRounds(cusGameRounds)
                .setPlayerList(cusGamePlayers)
                .build();
    }
}
