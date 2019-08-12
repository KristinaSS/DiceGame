package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.DiceRolled;
import dicegame.elements.Round;
import dicegame.enums.CombinationEnum;
import dicegame.elements.Player;

import java.util.*;

public class Game {
    private static Game gameInstance = null;

    private int rounds;
    private int playerCount;

    //Methods
    private Game() {
    }

    public static Game getInstance() {
        if (gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    //play or end Game

    void playGame() {

        List<Player> playerList = GameUtils.fillPlayerList(playerCount);
        DiceRolled diceRolled = DiceRolled.getInstance();
        Round currentRound;

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        for (int round = 1; round <= rounds; round++) {
            for(Player player: playerList) {
                diceRolled.resetElement();
                diceRolled.playElement();

                currentRound = new Round(player);
                currentRound.updatePlayerAndPrintPlayerRound(round);

                if (player.getPlayedCombinationsSet().contains(CombinationEnum.STRAIGHT)) {
                    endGame(playerList);
                }
            }
        }
        endGame(playerList);
    }

    private void endGame(List<Player> playerList) {
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");

        int placeInGame = 1;
        //playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));
        playerList.sort((o1, o2) -> {
            boolean hasGeneralaO1 = o1.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);
            boolean hasGeneralaO2 = o2.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA);

            int boolHasGeneralaCompare = Boolean.compare(hasGeneralaO2,hasGeneralaO1);

            if(boolHasGeneralaCompare!= 0)
                return boolHasGeneralaCompare;
            return Integer.compare(o2.getScore(),o1.getScore());
        });

        for (Player player : playerList) {
            GameUtils.printEndGamePlayerStats(placeInGame,player.getPlayerNumber(),player.getScore());
            placeInGame++;
        }

/*        long end = System.nanoTime();
        System.out.println("Took: " + ((end - Application.start) / 1000000) + " ms");
        System.out.println("Took: " + (end - Application.start)/ 1000000000 + " seconds");*/
        System.exit(0);
    }
}