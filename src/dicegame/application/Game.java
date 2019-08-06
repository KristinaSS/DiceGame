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
    private Map.Entry<CombinationEnum,Integer> entry;

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
                entry = null;
                Dice.resetDice();
                Dice.rollDice();
                evaluate(player,round);
                System.out.println();
                /*if(player.getPlayedCombinationsMap().containsKey(CombinationEnum.GENERALA))
                    GameUtils.endGame(playerList, player);*/
            }
        }
        GameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player player, int round) throws NullPointerException, ConcurrentModificationException{
        int oldScore = player.getScore();

        fillSortedScoreMap(player);

        if(entry!=null){
            player.getPlayedCombinationsMap().put(entry.getKey(),entry.getValue());
            player.setScore(player.getScore() + entry.getValue());
            GameUtils.printRound(player,
                    round,
                    oldScore,
                    entry.getValue(),
                    entry.getKey().getLabel());
            return;
        }

        GameUtils.printRound(player, round, oldScore, 0, "No Combination");
    }

    private void fillSortedScoreMap(Player player){
        if(Dice.listTemp.size() == 1){
            Dice.sortedScoreMap.put(CombinationEnum.GENERALA,
                    CombinationEnum.GENERALA.calculateCombination(findFirstValueGreaterThanOrEqualTo(5)));
            this.entry = Dice.sortedScoreMap.entrySet().iterator().next();
            return;
        }
        if(player.getPlayedCombinationsMap().size() == CombinationEnum.values().length-1){
            this.entry = null;
            return;
        }

        int fourOfAKind = checkFourOfAKind(player);

        checkForStraight(player);

        int triple = checkForTriple(player, fourOfAKind);

        int pair = checkForFullHouse(player, triple);

        checkForPair(triple, pair, player);

        checkForDoublePair(pair, player);

        Dice.sortedScoreMap = GameUtils.sortByValue(Dice.sortedScoreMap);

        if(Dice.sortedScoreMap.size()> 0)
            this.entry = Dice.sortedScoreMap.entrySet().iterator().next();
        else
            this.entry = null;
    }

    private int checkFourOfAKind(Player player){
        int foundFourOfAKind = findFirstValueGreaterThanOrEqualTo(4);
        if(foundFourOfAKind > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.FOUR_OF_A_KIND))) {
            Dice.sortedScoreMap.put(CombinationEnum.FOUR_OF_A_KIND,
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

        if(triple>0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.TRIPLE)))
            Dice.sortedScoreMap.put(CombinationEnum.TRIPLE,CombinationEnum.TRIPLE.calculateCombination(triple));
        return triple;
    }

    private int checkForFullHouse(Player player,int triple){
        Dice.listTemp.remove(triple);
        int pair = findFirstValueGreaterThanOrEqualTo(2);

        if(triple>0 && pair>0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.FULL_HOUSE)))
            Dice.sortedScoreMap.put(CombinationEnum.FULL_HOUSE,
                    CombinationEnum.FULL_HOUSE.calculateCombination((3*triple)+(2*pair)));
        return pair;
    }

    private void checkForPair(int triple, int pair, Player player){
        if(triple>0 && triple > pair && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.PAIR)))
            Dice.sortedScoreMap.put(CombinationEnum.PAIR, CombinationEnum.PAIR.calculateCombination(triple));
        else if(pair > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.PAIR))) {
            Dice.sortedScoreMap.put(CombinationEnum.PAIR, CombinationEnum.PAIR.calculateCombination(pair));
        }
    }

    private void checkForDoublePair(int pair, Player player){
        if(pair > 0){
            Dice.listTemp.remove(pair);
            int secondPair = findFirstValueGreaterThanOrEqualTo(2);
            if(secondPair > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.DOUBLE_PAIR)))
                Dice.sortedScoreMap.put(CombinationEnum.DOUBLE_PAIR,
                        CombinationEnum.DOUBLE_PAIR.calculateCombination(pair+secondPair));
        }
    }

    private void checkForStraight(Player player) {
        int straightCounter = 0;
        int beginningOfStraight = 0;

        if(Dice.listTemp.size()<5 || player.getPlayedCombinationsMap().containsKey(CombinationEnum.STRAIGHT))
            return;

        int i;
        for(i = 0; i< Dice.listTemp.size(); i++){
            System.out.println("straight " + i + " " + straightCounter);
            if(Dice.listTemp.containsKey(Dice.numberOfSides-i)){
                if(straightCounter == 0)
                    beginningOfStraight = Dice.numberOfSides-i;
                straightCounter++;
            }else
                straightCounter = 0;
            if(straightCounter == 5)
                break;
        }
        if(straightCounter == 5) {
            Dice.sortedScoreMap.put(CombinationEnum.STRAIGHT,
                    CombinationEnum.STRAIGHT.calculateCombination(beginningOfStraight));
        }
    }

    private static int findFirstValueGreaterThanOrEqualTo(int compareBy) {
        for (Map.Entry<Integer, Integer> entry : Dice.listTemp.entrySet()) {
            if (entry.getValue() >= compareBy) {
                return entry.getKey();
            }
        }
        return -1;
    }
}