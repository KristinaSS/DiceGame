package dicegame.application;

import java.util.Collections;
import java.util.List;

import dicegame.utils.*;
import dicegame.elements.Player;
import dicegame.elements.PlayerRound;
import dicegame.constants.CombinationEnum;

class Game {

    private int rounds;

    private List<Player> playerList;

    //Methods
    private Game() {
    }

    //play or end Game

    void playGame() {
        PlayerRound currentPlayerRound;


        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        for (int curRound = 1; curRound <= rounds; curRound++) {
            for (Player player : playerList) {

                currentPlayerRound = new PlayerRound(player, curRound);

                currentPlayerRound.playPlayerRound();

                if (player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA)) {
                    endGame();
                }
            }
        }
        endGame();
    }

    private void endGame() {
        //playerList.sort(Comparator.comparing(Player::getHasGenerala).thenComparing(Player::getScore)); // this is faster
        //playerList.sort(Player.comparator); //todo Ask Dancho if this is ok
        Collections.sort(playerList);

        Printer printer = new PrinterFactory().getPrinter("textfile");
        if (printer == null)
            System.out.println("Attention! Results have not been recorded!");
        else
            printer.printEndGamePlayerStats(playerList);

        long end = System.nanoTime();
        System.out.println("Took: " + ((end - Application.start) / 1000000) + " ms");
        System.out.println("Took: " + (end - Application.start) / 1000000000 + " seconds");
        System.exit(0);
    }

    static class Builder {
        private List<Player> playerList;
        private int rounds;

        Builder setPlayerList() {
            this.playerList = GameUtils.fillPlayerList(dicegame.constants.CommonConstants.PLAYER_COUNT);

            return this;
        }

        Builder setRounds() {
            this.rounds = dicegame.constants.CommonConstants.ROUND_COUNT;

            return this;
        }

        Game build() {
            Game game = new Game();
            game.playerList = this.playerList;
            game.rounds = this.rounds;

            return game;
        }
    }
}