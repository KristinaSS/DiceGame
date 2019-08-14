package dicegame.elements;

import dicegame.constants.CombinationEnum;

import java.util.*;

public final class Player
{
	private int playerNumber;

	private int score = 0;

	private Set<CombinationEnum> playedCombinationsSet = new HashSet<>();

	/*public static Comparator<Player> comparator = (o1, o2) -> {
		boolean hasGeneralaO1 = o1.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);
		boolean hasGeneralaO2 = o2.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);

		int boolHasGeneralaCompare = Boolean.compare(hasGeneralaO2, hasGeneralaO1);

		if (boolHasGeneralaCompare != 0)
			return boolHasGeneralaCompare;
		return Integer.compare(o2.getScore(), o1.getScore());
	};*/

	private int hasGenerala = 0; //todo delete if not needed

	//Methods
	public Player(int playerNumber)
	{
		this.playerNumber = playerNumber;
	}

	public int getPlayerNumber()
	{
		return playerNumber;
	}

	public int getHasGenerala()
	{
		return hasGenerala;
	}

	public void setHasGenerala(int hasGenerala)
	{
		this.hasGenerala = hasGenerala;
	}

	public Set<CombinationEnum> getPlayedCombinationsSet()
	{
		return playedCombinationsSet;
	}

	public int getScore()
	{
		return score;
	}

	void setScore(int score)
	{
		this.score = score;
	}
	
	public void rollDice(){
		DiceRolled.rotateElement();
	}

}
