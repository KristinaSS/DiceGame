package dicegame.elements;

import dicegame.interfaces.GameElementActions;

import java.util.*;

public class DiceRolled implements GameElementActions
{
	public static int numberOfDice;

	private static DiceRolled diceRolledInstance = null;

	private final static List<Die> diceRolled = new ArrayList<>();

	private final static Map<Integer, Integer> timesRepeatedEachDice = new TreeMap<>(Collections.reverseOrder());

	//getters and setters and constructors

	private DiceRolled()
	{
	}

	public static DiceRolled getInstance()
	{
		if (diceRolledInstance == null)
			diceRolledInstance = new DiceRolled();
		return diceRolledInstance;
	}

	static Map<Integer, Integer> getTimesRepeatedEachDice()
	{
		return timesRepeatedEachDice;
	}

	//implemented methods

	public void rotateElement()
	{
		int dieSide;

		for (Die die : diceRolled)
		{
			dieSide = die.rollDie();

			timesRepeatedEachDice.put(dieSide, timesRepeatedEachDice.getOrDefault(dieSide, 0) + 1);
		}

	}

	public void resetElement()
	{
		//diceRolled.clear();
		//Hey Dancho, should i delete this completely and move this function call somewhere else like in play
		//element or should i keep it
		timesRepeatedEachDice.clear();
	}

	//other Methods

	static String rolledDiceListToString()
	{
		diceRolled.sort(Comparator.comparingInt(Die::getDieSide));
		StringBuilder diceRolledSB = new StringBuilder(" ");
		for (Die die : diceRolled)
		{
			diceRolledSB.append(die.getDieSide()).append(", ");
		}
		return diceRolledSB.toString();
	}

	static int findFirstValueGreaterThanOrEqualTo(int compareBy)
	{
		for (Map.Entry<Integer, Integer> entry : timesRepeatedEachDice.entrySet())
		{
			if (entry.getValue() >= compareBy)
			{
				return entry.getKey();
			}
		}
		return -1;
	}

	public static void initializeDiceRolled() //todo ask Dancho is this better here or in utils?
	{
		for (int i = 0; i < numberOfDice; i++)
		{
			diceRolled.add(new Die());
		}
	}
	//when utils when not in utils, validation methods not in utils
}
