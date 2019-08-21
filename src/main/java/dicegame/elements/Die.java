package dicegame.elements;

import java.util.concurrent.ThreadLocalRandom;

import dicegame.utils.GameUtils;
import dicegame.constants.CommonConstants;

class Die {
    private int dieSide;

    static final int numberOfSides = Integer
            .parseInt(GameUtils.readPropertiesFile().getProperty(CommonConstants.NUMBER_OF_SIDES_STR));

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
