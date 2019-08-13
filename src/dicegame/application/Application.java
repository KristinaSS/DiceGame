package dicegame.application;

import dicegame.GameUtils;
import dicegame.enums.PathEnum;

import java.nio.file.Paths;

public class Application
{
	static long start;

	public static void main(String[] args)
	{
		start = System.nanoTime();

		Game diceGame = Game.getInstance();
		GameUtils.readPropertiesFile(Paths.get(PathEnum.KRISTINA_WORK_PATH.getFilePathStr()));

		//game is ready to be played
		diceGame.playGame();
	}
}
