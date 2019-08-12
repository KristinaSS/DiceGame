package dicegame.elements;

import java.util.concurrent.ThreadLocalRandom;

public class Die{
    private int dieSide;
    public static int numberOfSides;

    Die() {
        dieSide = ThreadLocalRandom.current().nextInt(numberOfSides)+1;
    }

    public int getDieSide() {
        return dieSide;
    }
}
