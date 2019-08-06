package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static Game gameInstance = null;

    private int rounds;
    private int playerCount;
    private int largestRoundScore = 0;
    private CombinationEnum playedRoundCombination = null;

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

    void playGame() throws NullPointerException {

        List<Player> playerList = GameUtils.fillPlayerList(playerCount);

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        for(int round = 1; round<= rounds; round++) {
            System.out.println();
            for (Player player : playerList) {
                largestRoundScore = 0;
                Dice.resetDice();
                Dice.rollDice();
                evaluate(player,round);
                System.out.println();
                /*if(player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA))
                    GameUtils.endGame(playerList, player);*/
            }
        }
        GameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player player, int round) throws NullPointerException{
        int oldScore = player.getScore();

        fillSortedScoreMap(player);

        if(largestRoundScore>0){
            player.getPlayedCombinationsSet().add(playedRoundCombination);
            player.setScore(player.getScore() + largestRoundScore);
            GameUtils.printRound(player,
                    round,
                    oldScore,
                    largestRoundScore,
                    playedRoundCombination.getLabel());
            return;
        }

        GameUtils.printRound(player, round, oldScore, 0, "No Combination");
    }

    private void fillSortedScoreMap(Player player){
        if(Dice.getBucketSortTreeMap().size() == 1 && !player.getPlayedCombinationsSet().contains(CombinationEnum.GENERALA)){
            this.playedRoundCombination = CombinationEnum.GENERALA;
            this.largestRoundScore = CombinationEnum
                    .GENERALA.calculateCombination(findFirstValueGreaterThanOrEqualTo(5));
            return;
        }

        if(player.getPlayedCombinationsSet().size() >= CombinationEnum.values().length-1)
            return;

        int fourOfAKind = checkFourOfAKind(player);

        checkForStraight(player);

        int triple = checkForTriple(player, fourOfAKind);

        int pair = checkForFullHouse(player, triple);

        checkForPair(triple, pair, player);

        checkForDoublePair(pair, player);

        Dice.setSortedScoreMap(GameUtils.sortByValue(Dice.getSortedScoreMap()));

        if(Dice.getSortedScoreMap().size()> 0){
            this.largestRoundScore = Dice.getSortedScoreMap().entrySet().iterator().next().getValue();
            this.playedRoundCombination = Dice.getSortedScoreMap().entrySet().iterator().next().getKey();

        }
    }

    private int checkFourOfAKind(Player player){
        int foundFourOfAKind = findFirstValueGreaterThanOrEqualTo(4);
        if(foundFourOfAKind > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.FOUR_OF_A_KIND))) {
            Dice.getSortedScoreMap().put(CombinationEnum.FOUR_OF_A_KIND,
                    CombinationEnum.FOUR_OF_A_KIND.calculateCombination(foundFourOfAKind));
            return foundFourOfAKind;
        }
        return -1;
    }

    private int checkForTriple(Player player, int fourOfAKind){
        int triple;
        if(fourOfAKind>0)
            triple = fourOfAKind;
        else
            triple= findFirstValueGreaterThanOrEqualTo(3);

        if(triple>0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.TRIPLE)))
            Dice.getSortedScoreMap().put(CombinationEnum.TRIPLE,CombinationEnum.TRIPLE.calculateCombination(triple));
        return triple;
    }

    private int checkForFullHouse(Player player,int triple){
        Dice.getBucketSortTreeMap().remove(triple);
        int pair = findFirstValueGreaterThanOrEqualTo(2);

        if(triple>0 && pair>0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.FULL_HOUSE)))
            Dice.getSortedScoreMap().put(CombinationEnum.FULL_HOUSE,
                    CombinationEnum.FULL_HOUSE.calculateCombination((3*triple)+(2*pair)));
        return pair;
    }

    private void checkForPair(int triple, int pair, Player player){
        if(triple>0 && triple > pair && !(player.getPlayedCombinationsSet().contains(CombinationEnum.PAIR)))
            Dice.getSortedScoreMap().put(CombinationEnum.PAIR, CombinationEnum.PAIR.calculateCombination(triple));
        else if(pair > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.PAIR))) {
            Dice.getSortedScoreMap().put(CombinationEnum.PAIR, CombinationEnum.PAIR.calculateCombination(pair));
        }
    }

    private void checkForDoublePair(int pair, Player player){
        if(pair > 0){
            Dice.getBucketSortTreeMap().remove(pair);
            int secondPair = findFirstValueGreaterThanOrEqualTo(2);
            if(secondPair > 0 && !(player.getPlayedCombinationsSet().contains(CombinationEnum.DOUBLE_PAIR)))
                Dice.getSortedScoreMap().put(CombinationEnum.DOUBLE_PAIR,
                        CombinationEnum.DOUBLE_PAIR.calculateCombination(pair+secondPair));
        }
    }

    private void checkForStraight(Player player) {
        int straightCounter = 0;
        int beginningOfStraight = 0;

        if(Dice.getBucketSortTreeMap().size()<5 || player.getPlayedCombinationsSet().contains(CombinationEnum.STRAIGHT))
            return;

        int i;
        for(i = 0; i< Dice.getBucketSortTreeMap().size(); i++){
            if(Dice.getBucketSortTreeMap().containsKey(Dice.numberOfSides-i)){
                if(straightCounter == 0)
                    beginningOfStraight = Dice.numberOfSides-i;
                straightCounter++;
            }else
                straightCounter = 0;
            if(straightCounter == 5)
                break;
        }
        if(straightCounter == 5) {
            Dice.getSortedScoreMap().put(CombinationEnum.STRAIGHT,
                    CombinationEnum.STRAIGHT.calculateCombination(beginningOfStraight));
        }
    }

    private static int findFirstValueGreaterThanOrEqualTo(int compareBy) {
        for (Map.Entry<Integer, Integer> entry : Dice.getBucketSortTreeMap().entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey();
            }
        }
        return -1;
    }
}