package dicegame.elements;

import dicegame.enums.CombinationEnum;

import java.util.Map;

import static dicegame.elements.DiceRolled.findFirstValueGreaterThanOrEqualTo;

public class PlayerRound
{
	private int round;

	private Player player;

	private int maxRoundScore;

	private CombinationEnum comboForMaxScore;

	public PlayerRound(Player player, int round)
	{
		this.round = round;
		this.player = player;
		this.maxRoundScore = 0;
		this.comboForMaxScore = null;
	}

	//public methods

	public void playPlayerRound()
	{
		int oldScore = player.getScore();

		callMethodsToUpdateScore();

		if (maxRoundScore > 0)
		{
			player.getPlayedCombinationsSet().add(comboForMaxScore);
			player.setScore(player.getScore() + maxRoundScore);
			printRound(oldScore);
			return;
		}
		printRound(oldScore);
	}

	//caller methods

	private void callMethodsToUpdateScore()
	{

		updateScoreIfGenerala();

		if (player.getPlayedCombinationsSet().size() >= CombinationEnum.values().length - 1 || maxRoundScore > 0)
			return;

		updateScoreIfStraight();

		int fourOfAKind = getFourOfAKind();
		int triple = getTriple(fourOfAKind);
		int pair = getPair(triple);
		int secondPair = getSecondPair(pair);

		updateScoreIfFourOfAKind(fourOfAKind);

		updateRoundScoreIfTripleValid(triple);

		updateRoundScoreIfFullHouseValid(triple, pair);

		updateRoundScoreIfPairValid(triple, pair);

		updateRoundScoreIfDoublePairValid(pair, secondPair);
	}

	private void printRound(int oldScore)
	{

		System.out.println(">>> round: " + round);
		System.out.println(">player " + player.getPlayerNumber() + ":");
		System.out.println("current score: " + oldScore);
		System.out.println("dice roll:" + DiceRolled.rolledDiceListToString() + "-> " + (comboForMaxScore != null ?
				comboForMaxScore.getLabel() :
				"No combination") + " (" + maxRoundScore + ") ");
		System.out.println("new score: " + player.getScore());
		System.out.println();
	}

	private void updateRoundScore(int score, CombinationEnum combo)
	{
		if (score > this.maxRoundScore)
		{
			this.maxRoundScore = score;
			this.comboForMaxScore = combo;
		}
	}

	//Updating score and getting combo die numbers todo ask about static

	private void updateScoreIfGenerala()
	{
		CombinationEnum generala = CombinationEnum.GENERALA;
		if (DiceRolled.getTimesRepeatedEachDice().size() != 1)
			return;

		if (!player.getPlayedCombinationsSet().contains(generala))
		{
			comboForMaxScore = generala;
			maxRoundScore = generala.calculateCombination(findFirstValueGreaterThanOrEqualTo(generala.getDiceCount()));
		}
	}

	private void updateScoreIfStraight()
	{
		int straightCounter = 0;
		int beginningOfStraight = 0;
		CombinationEnum straight = CombinationEnum.STRAIGHT;
		Map<Integer, Integer> timesRepeatedEachDieSideMap = DiceRolled.getTimesRepeatedEachDice();

		if (timesRepeatedEachDieSideMap.size() < straight.getDiceCount()) //if mapsize == 5
			return;

		if (player.getPlayedCombinationsSet().contains(straight)) //if player has already played straight
			return;

		//System.out.println("Enter straight");
		int i;
		for (i = 0; i < Die.numberOfSides; i++)
		{
			if (timesRepeatedEachDieSideMap.containsKey(Die.numberOfSides - i))
			{
				if (straightCounter == 0)
				{
					beginningOfStraight = Die.numberOfSides - i;
				}
				straightCounter++;
			}
			else
				straightCounter = 0;
			if (straightCounter == straight.getDiceCount())
			{
				//int temp = straight.calculateCombination(beginningOfStraight);
				updateRoundScore(straight.calculateCombination(beginningOfStraight), straight);
			}
		}
	}

	private void updateScoreIfFourOfAKind(int foundFourOfAKind)
	{
		CombinationEnum fourOfAKind = CombinationEnum.FOUR_OF_A_KIND;
		if (foundFourOfAKind <= 0)
			return;
		if (player.getPlayedCombinationsSet().contains(fourOfAKind))
			return;

		int tempScore = fourOfAKind.calculateCombination(foundFourOfAKind);
		updateRoundScore(tempScore, fourOfAKind);
	}

	private void updateRoundScoreIfTripleValid(int tripleDie)
	{
		CombinationEnum triple = CombinationEnum.TRIPLE;
		if (tripleDie <= 0)
			return;
		if (player.getPlayedCombinationsSet().contains(triple))
			return;

		int tempScore = triple.calculateCombination(tripleDie);
		updateRoundScore(tempScore, triple);
	}

	private void updateRoundScoreIfFullHouseValid(int triple, int pair)
	{
		CombinationEnum fullHouse = CombinationEnum.FULL_HOUSE;
		if (triple <= 0 || pair <= 0)
			return;
		if (player.getPlayedCombinationsSet().contains(fullHouse))
			return;

		int tempScore = fullHouse.calculateCombination((3 * triple) + (2 * pair));
		updateRoundScore(tempScore, fullHouse);
	}

	private void updateRoundScoreIfPairValid(int tripleDie, int pairDie)
	{
		CombinationEnum pair = CombinationEnum.PAIR;
		int tempScore;
		if (player.getPlayedCombinationsSet().contains(pair))
			return;
		if (tripleDie > 0 && tripleDie > pairDie)
		{
			tempScore = pair.calculateCombination(tripleDie);
			updateRoundScore(tempScore, pair);
		}
		else if (pairDie > 0)
		{
			tempScore = pair.calculateCombination(pairDie);
			updateRoundScore(tempScore, pair);
		}
	}

	private void updateRoundScoreIfDoublePairValid(int pair, int secondPair)
	{
		CombinationEnum doublePair = CombinationEnum.DOUBLE_PAIR;
		if (secondPair > 0 && !(player.getPlayedCombinationsSet().contains(doublePair)))
		{
			int tempScore = doublePair.calculateCombination(pair + secondPair);
			updateRoundScore(tempScore, doublePair);
		}
	}

	private int getFourOfAKind()
	{
		System.out.println(CombinationEnum.FOUR_OF_A_KIND.getDiceCount());
		return findFirstValueGreaterThanOrEqualTo(CombinationEnum.FOUR_OF_A_KIND.getDiceCount());
	}

	private int getTriple(int fourOfAKind)
	{
		int triple = findFirstValueGreaterThanOrEqualTo(CombinationEnum.TRIPLE.getDiceCount());
		if (fourOfAKind > triple)
			triple = fourOfAKind;
		return triple;
	}

	private int getPair(int triple)
	{
		DiceRolled.getTimesRepeatedEachDice().remove(triple);
		return findFirstValueGreaterThanOrEqualTo(CombinationEnum.PAIR.getDiceCount());
	}

	private int getSecondPair(int pair)
	{
		if (pair > 0)
		{
			DiceRolled.getTimesRepeatedEachDice().remove(pair);
			return findFirstValueGreaterThanOrEqualTo(CombinationEnum.DOUBLE_PAIR.getDiceCount());
		}
		return -1;
	}
}
