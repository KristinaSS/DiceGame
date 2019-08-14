package dicegame.application;

import dicegame.GameUtils;
import dicegame.constants.CommonConstants;

public class Application {
	static long start;

	public static void main(String[] args) {
		start = System.nanoTime();

		Game diceGame = Game.getInstance()
				.setRounds(Integer.parseInt(GameUtils.readPropertiesFile().getProperty(CommonConstants.ROUND_COUNT)))
				.addPlayers(Integer.parseInt(GameUtils.readPropertiesFile().getProperty(CommonConstants.PLAYER_COUNT)));

		// game is ready to be played
		diceGame.playGame();
	}
}
