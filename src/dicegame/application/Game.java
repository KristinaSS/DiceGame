package dicegame.application;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import dicegame.exceptions.IllegalPrinterTypeException;
import dicegame.utils.*;
import dicegame.elements.Player;
import dicegame.elements.PlayerRound;
import dicegame.constants.CombinationEnum;
import dicegame.utils.printerfactory.Printer;
import dicegame.utils.printerfactory.PrinterFactory;

public class Game {

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
        //playerList.sort(Player.comparator);
        Collections.sort(playerList);
        String printerTypeStr = "textfile";

        Printer printer = new PrinterFactory().getPrinter(printerTypeStr);
        try {
            if (printer == null)
                throw new IllegalPrinterTypeException("No such printer type: "+ printerTypeStr);
            else
                printer.printEndGamePlayerStats(playerList);
        }catch (IllegalPrinterTypeException e){
            Application.LOGGER.log(Level.WARNING, "An IllegalPrinterTypeException was thrown", e);
            System.exit(-1 );
        }

        long end = System.nanoTime();
        System.out.println("Took: " + ((end - Application.start) / 1000000) + " ms");
        System.out.println("Took: " + (end - Application.start) / 1000000000 + " seconds");
        System.exit(0);
    }

    public static class Builder {
        private List<Player> playerList;
        private int rounds;

        public Builder setPlayerList(int playerCount) {
            assert playerCount>0:"Players need to be more than 0";
            this.playerList = GameUtils.fillPlayerList(playerCount);

            return this;
        }

        public Builder setRounds(int rounds) {
            assert rounds> 0:"Rounds need to be more than 0";
            this.rounds = rounds;

            return this;
        }

        public Game build() {
            Game game = new Game();
            game.playerList = this.playerList;
            game.rounds = this.rounds;

            return game;
        }
    }
}