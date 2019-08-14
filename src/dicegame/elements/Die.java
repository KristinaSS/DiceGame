package dicegame.elements;

import java.util.concurrent.ThreadLocalRandom;

import dicegame.GameUtils;
import dicegame.constants.CommonConstants;

public class Die {
	private int dieSide;

	public static final int numberOfSides = Integer
			.parseInt(GameUtils.readPropertiesFile().getProperty(CommonConstants.NUMBER_OF_SIDES));

	Die() {
	}

	int getDieSide() {
		return dieSide;
	}

	int rollDie() {
		dieSide = ThreadLocalRandom.current().nextInt(numberOfSides) + 1;
		return dieSide;
	}

}
