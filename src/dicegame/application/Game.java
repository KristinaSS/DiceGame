package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;

import static dicegame.GameUtils.findFirstValueGreaterThanOrEqualTo;

public class Game {
    private static Game gameInstance = null;
    private static int tempScore;

    private int rounds;
    private int playerCount;
    private int maxCurRoundCurPlayerScore = 0;
    private CombinationEnum comboForMaxScore = null;

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

    //play Game

    void playGame() {

        List<Player> playerList = GameUtils.fillPlayerList(playerCount);

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        for (int round = 1; round <= rounds; round++) {
            System.out.println();
            for (Player player : playerList) {
                maxCurRoundCurPlayerScore = 0;
                Dice.resetDice();
                Dice.rollDice();
                updatePlayerAndPrintPlayerRound(player, round);
                System.out.println();
                /*if(player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA))
                    GameUtils.endGame(playerList, player);*/
            }
        }
        endGame(playerList, null);
    }

    private void endGame(List<Player> playerList, Player playerWithGenerala) {
        int previousScore = -1;
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");

        int placeInGame = 1;
        playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        if (!(playerWithGenerala == null)) {
            playerList.remove(playerWithGenerala);
            GameUtils.printEndGamePlayerStats(1,playerWithGenerala);
        }

        for (Player player : playerList) {
            if (previousScore > player.getScore() || previousScore == -1)
                placeInGame++;
            if(playerWithGenerala == null && previousScore == -1)
                placeInGame--;
            GameUtils.printEndGamePlayerStats(placeInGame,player);
            previousScore = player.getScore();
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    //evaluation

    private void updatePlayerAndPrintPlayerRound(Player player, int round) {
        int oldScore = player.getScore();

        callMethodsForUpdatingPlayerRoundScore(player);

        if (maxCurRoundCurPlayerScore > 0) {
            player.getPlayedCombinationsSet().add(comboForMaxScore);
            player.setScore(player.getScore() + maxCurRoundCurPlayerScore);
            GameUtils.printRound(player,
                    round,
                    oldScore,
                    maxCurRoundCurPlayerScore,
                    comboForMaxScore.getLabel());
            return;
        }
        GameUtils.printRound(player, round, oldScore, 0, "No Combination");
    }

    private void callMethodsForUpdatingPlayerRoundScore(Player player) {

        updateRoundScoreIfGeneralaValid(player);

        if (player.getPlayedCombinationsSet().size() >= CombinationEnum.values().length - 1
                || this.maxCurRoundCurPlayerScore >0 )
            return;

        updateRoundScoreIfStraightValid(player);

        int fourOfAKind = getFourOfAKind();
        int triple = getTriple(fourOfAKind);
        int pair = getPair(triple);
        int secondPair = getSecondPair(pair);

        updateRoundScoreIfFourOfAKindValid(player, fourOfAKind);

        updateRoundScoreIfTripleValid(triple,player);

        updateRoundScoreIfFullHouseValid(player, triple, pair);

        updateRoundScoreIfPairValid(triple, pair, player);

        updateRoundScoreIfDoublePairValid(pair, player, secondPair);

        tempScore = 0;
    }

    //Updating score and getting combo die numbers

    private void updateRoundScoreIfGeneralaValid(Player player){
        if (Dice.getTimesRepeatedEachDieSideTreeMap().size() == 1 &&
                !player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA)) {
            this.comboForMaxScore = CombinationEnum.GENERALA;
            this.maxCurRoundCurPlayerScore = CombinationEnum
                    .GENERALA.calculateCombination(findFirstValueGreaterThanOrEqualTo(5));
        }
    }

    private void updateRoundScoreIfStraightValid(Player player) {
        int straightCounter = 0;
        int beginningOfStraight = 0;

        if (Dice.getTimesRepeatedEachDieSideTreeMap().size() < 5 || player.getPlayedCombinationsSet().contains(CombinationEnum.STRAIGHT))
            return;

        int i;
        for (i = 0; i < Dice.getTimesRepeatedEachDieSideTreeMap().size(); i++) {
            if (Dice.getTimesRepeatedEachDieSideTreeMap().containsKey(Dice.numberOfSides - i)) {
                if (straightCounter == 0)
                    beginningOfStraight = Dice.numberOfSides - i;
                straightCounter++;
            } else
                straightCounter = 0;
            if (straightCounter == 5)
                break;
        }
        if (straightCounter == 5) {
            tempScore = CombinationEnum.STRAIGHT.calculateCombination(beginningOfStraight);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.STRAIGHT;
            }
        }
    }

    private void updateRoundScoreIfFourOfAKindValid(Player player, int foundFourOfAKind){
        if (foundFourOfAKind > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.FOUR_OF_A_KIND))) {
            tempScore = CombinationEnum.FOUR_OF_A_KIND.calculateCombination(foundFourOfAKind);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.FOUR_OF_A_KIND;
            }
        }
    }

    private void updateRoundScoreIfTripleValid(int triple, Player player){
        if (triple > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.TRIPLE))) {
            tempScore = CombinationEnum.TRIPLE.calculateCombination(triple);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.TRIPLE;
            }
        }
    }

    private void updateRoundScoreIfFullHouseValid(Player player, int triple, int pair) {
        if (triple > 0 && pair > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.FULL_HOUSE))) {
            tempScore = CombinationEnum.FULL_HOUSE.calculateCombination((3 * triple) + (2 * pair));
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.FULL_HOUSE;
            }
        }
    }

    private void updateRoundScoreIfPairValid(int triple, int pair, Player player) {
        if (triple > 0
                && triple > pair
                && !(player.getPlayedCombinationsSet().contains(CombinationEnum.PAIR))) {
            tempScore = CombinationEnum.PAIR.calculateCombination(triple);
            if (tempScore > this.maxCurRoundCurPlayerScore)
                this.maxCurRoundCurPlayerScore = tempScore;
        } else if (pair > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.PAIR))) {
            tempScore = CombinationEnum.PAIR.calculateCombination(pair);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.PAIR;
            }
        }
    }

    private void updateRoundScoreIfDoublePairValid(int pair, Player player, int secondPair) {
        if (secondPair > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.DOUBLE_PAIR))) {
            tempScore = CombinationEnum.DOUBLE_PAIR.calculateCombination(pair + secondPair);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = CombinationEnum.DOUBLE_PAIR;
            }
        }
    }

    private int getFourOfAKind() {
        return findFirstValueGreaterThanOrEqualTo(4);
    }

    private int getTriple(int fourOfAKind) {
        int triple = findFirstValueGreaterThanOrEqualTo(3);
        if (fourOfAKind > triple)
            triple = fourOfAKind;
        return triple;
    }

    private int getPair(int triple){
        Dice.getTimesRepeatedEachDieSideTreeMap().remove(triple);
        return findFirstValueGreaterThanOrEqualTo(2);
    }

    private int getSecondPair(int pair) {
        if (pair > 0) {
            Dice.getTimesRepeatedEachDieSideTreeMap().remove(pair);
            return findFirstValueGreaterThanOrEqualTo(2);
        }
        return -1;
    }
}