package dicegame.constants;

import dicegame.utils.GameUtils;

public class CommonConstants {
    private static final String ROUND_COUNT_STR = "roundCount";
    private static final String PLAYER_COUNT_STR = "playerCount";
    private static final String DICE_COUNT_STR = "diceCount";
    public static final String NUMBER_OF_SIDES_STR = "numberOfSidesOnDice";
    public static final String PATH = "config.properties";

    //I dont know if this is ok
    public static final int ROUND_COUNT = (Integer.parseInt
            (GameUtils.readPropertiesFile().getProperty(ROUND_COUNT_STR)));
    public static final int PLAYER_COUNT = (Integer.parseInt
            (GameUtils.readPropertiesFile().getProperty(PLAYER_COUNT_STR)));
    public static final int DICE_COUNT = (Integer.parseInt
            (GameUtils.readPropertiesFile().getProperty(DICE_COUNT_STR)));


}
