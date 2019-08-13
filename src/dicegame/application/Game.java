package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.DiceRolled;
import dicegame.elements.PlayerRound;
import dicegame.enums.CombinationEnum;
import dicegame.elements.Player;

import java.util.*;

public class Game
{
	private static Game gameInstance = null;

	private int rounds;

	private int playerCount;

	//Methods
	private Game()
	{
	}

	public static Game getInstance()
	{
		if (gameInstance == null)
			gameInstance = new Game();
		return gameInstance;
	}

	public void setRounds(int rounds)
	{
		this.rounds = rounds;
	}

	public void setPlayerCount(int playerCount)
	{
		this.playerCount = playerCount;
	}

	//play or end Game

	void playGame()
	{

		List<Player> playerList = GameUtils.fillPlayerList(playerCount);
		DiceRolled diceRolled = DiceRolled.getInstance();
		PlayerRound currentPlayerRound;

		diceRolled.initializeDiceRolled();

		System.out.println(">>> WELCOME TO THE DICE GAME <<<");

		for (int curRound = 1; curRound <= rounds; curRound++)
		{
			for (Player player : playerList)
			{
				diceRolled.resetElement();
				diceRolled.rotateElement();

				currentPlayerRound = new PlayerRound(player, curRound);
				currentPlayerRound.playPlayerRound();

				if (player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA))
				{
					player.setHasGenerala(1);
					//endGame(playerList);
				}
			}
		}
		endGame(playerList);
	}

	private void endGame(List<Player> playerList)
	{
		System.out.println(">>>  RESULTS  <<<<");
		System.out.println("Place       Player       Score");

		int placeInGame = 1;

		playerList.sort(Comparator.comparing(Player::getHasGenerala).thenComparing(Player::getScore)); // this is faster
		//playerList.sort(Player.comparator); //todo Ask Dancho if this is ok

		for (Player player : playerList)
		{
			GameUtils.printEndGamePlayerStats(placeInGame, player.getPlayerNumber(), player.getScore());
			placeInGame++;
		}

		long end = System.nanoTime();
		System.out.println("Took: " + ((end - Application.start) / 1000000) + " ms");
		System.out.println("Took: " + (end - Application.start) / 1000000000 + " seconds");
		System.exit(0);
	}
}