package dicegame.application;

import java.util.Comparator;
import java.util.List;

import dicegame.GameUtils;
import dicegame.elements.Player;
import dicegame.elements.PlayerRound;
import dicegame.constants.CombinationEnum;

public class Game
{
	private static Game gameInstance = null;

	private int rounds;
	
	List<Player> playerList;

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

	public Game setRounds(int rounds)
	{
		this.rounds = rounds;
		return gameInstance;
	}

	public Game addPlayers(int playerCount)
	{
		this.playerList = GameUtils.fillPlayerList(playerCount);
		return gameInstance;
	}

	//play or end Game

	void playGame()
	{
		PlayerRound currentPlayerRound;


		System.out.println(">>> WELCOME TO THE DICE GAME <<<");

		for (int curRound = 1; curRound <= rounds; curRound++)
		{
			for (Player player : playerList)
			{
				
				currentPlayerRound = new PlayerRound(player, curRound);

				currentPlayerRound.playPlayerRound();

				if (player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA))
				{
					player.setHasGenerala(1);
					//endGame(playerList);
				}
			}
		}
		endGame();
	}

	private void endGame()
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