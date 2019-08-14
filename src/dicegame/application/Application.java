package dicegame.application;

public class Application {
    static long start;

    public static void main(String[] args) {
        start = System.nanoTime();

        Game diceGame = new Game.Builder()
                .setRounds()
                .setPlayerList()
                .build();

        // game is ready to be played
        diceGame.playGame();
    }
}
