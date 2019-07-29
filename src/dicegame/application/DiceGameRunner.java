package dicegame.application;

public class DiceGameRunner {
    public static void main(String[] args) {
        int numberOfGames = 3;
        Game diceGame;
        while(numberOfGames-->0){
            diceGame = new Game(5,3);
            diceGame.playGame();
        }
    }
}
