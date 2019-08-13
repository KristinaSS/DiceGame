package dicegame.elements;

import java.util.concurrent.ThreadLocalRandom;

public class Die
{
	private int dieSide;

	public static int numberOfSides;

	Die()
	{
	}

	int getDieSide()
	{
		return dieSide;
	}

	int rollDie()
	{
		dieSide = ThreadLocalRandom.current().nextInt(numberOfSides) + 1;
		return dieSide;
	}

}

