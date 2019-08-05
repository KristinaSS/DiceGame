package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;

public class Game {
    private static Game gameInstance = null;

    private int rounds;
    private int playerCount;
    private int roundScore = 0;
    private CombinationEnum highestPlayedCombination = null;

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
                Dice.resetDice();
                Dice.rollDice();
                Dice.sortDiceReverseOrder(Dice.diceRolledList);
                evaluate(player,round);
                if (player.getPlayedCombinationsMap().containsKey(CombinationEnum.GENERALA)) {
                    GameUtils.endGame(playerList, player);
                    return;
                }
                System.out.println();
            }
        }
        GameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player player, int round) throws NullPointerException {
        int oldScore = player.getScore();

        fillSortedMap();
        findLargestCombination(player);

        if(roundScore!= 0){
            player.getPlayedCombinationsMap().put(highestPlayedCombination,roundScore);
            player.setScore(player.getScore() + roundScore);
            GameUtils.printRound(player,
                    round,
                    oldScore,
                    roundScore,
                    highestPlayedCombination.getLabel());
            return;
        }
        GameUtils.printRound(player, round, oldScore, 0, "No Combination");
    }

    //checking for combinations

    private void fillSortedMap(){
        int score;

        score = calculatePair(Dice.diceRolledList);
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.PAIR,score);

        score = calculateDoublePair();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.DOUBLE_PAIR,score);

        score = calculateTriple();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.TRIPLE,score);

        score = calculateFullHouse();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.FULL_HOUSE,score);

        score = calculateFourOfAKind();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.FOUR_OF_A_KIND,score);

        score = calculateStraight();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.STRAIGHT,score);

        score = calculateGenerala();
        if(score > 0)
            Dice.sortedScoreMap.put(CombinationEnum.GENERALA,score);

        //System.out.println(Dice.sortedScoreMap);
    }

    private void findLargestCombination(Player player){
        GameUtils.sortByValue();
        //System.out.println(Dice.sortedScoreMap);
        for (Map.Entry<CombinationEnum, Integer> entry : Dice.sortedScoreMap.entrySet()){
            if(player.getPlayedCombinationsMap().containsKey(entry.getKey()))
                continue;
            this.roundScore = entry.getValue();
            this.highestPlayedCombination = entry.getKey();
            return;
        }
        this.roundScore = 0;
        this.highestPlayedCombination = null;
    }

    private int calculatePair(List<Integer> rolledDice) {
        for (int i = 1; i < rolledDice.size(); i++) {
            if (Dice.diceRolledList.get(i-1).compareTo(Dice.diceRolledList.get(i)) == 0) {
                return CombinationEnum.PAIR.calculateCombination(Dice.diceRolledList.get(i));
            } }
        return 0;
    }

    private int calculateDoublePair() {
        int bothPairs = 0;
        for (int i = 1; i < Dice.diceRolledList.size(); i++) {
            if (Dice.diceRolledList.get(i-1).compareTo(Dice.diceRolledList.get(i)) == 0) {
                if (bothPairs == 0) {
                    bothPairs = Dice.diceRolledList.get(i);
                    i = ++i;
                    continue;
                }
                bothPairs += Dice.diceRolledList.get(i);
                return CombinationEnum.DOUBLE_PAIR.calculateCombination(bothPairs);
            }
        }
        return 0;
    }

    private int calculateTriple() {
        for (int i = 2; i < Dice.diceRolledList.size(); i++) {
            if (Dice.diceRolledList.get(i-2).compareTo(Dice.diceRolledList.get(i)) == 0){
                return CombinationEnum.TRIPLE.calculateCombination(Dice.diceRolledList.get(i));
            }
        }
        return 0;
    }

    private int calculateFullHouse() {
        List<Integer> sortedListWithoutTriple = new ArrayList<>(Dice.diceRolledList);
        int tripleDie = (calculateTriple()-CombinationEnum.TRIPLE.getValue())/3;
        sortedListWithoutTriple.removeAll(Collections.singleton(tripleDie));
        int pairDie = (calculatePair(sortedListWithoutTriple)-CombinationEnum.PAIR.getValue())/2;

        if(tripleDie <= 0 || pairDie <= 0)
            return 0;
        return CombinationEnum.FULL_HOUSE.calculateCombination((3*tripleDie)+(2*pairDie));
    }

    private int calculateStraight() {
        int straightCounter = 0;

        Set<Integer> diceRolledSet = new HashSet<>(Dice.diceRolledList);
        List<Integer>noDuplicateList = new ArrayList<>(diceRolledSet);
        Dice.sortDiceReverseOrder(noDuplicateList);

        if(noDuplicateList.size()<5)
            return 0;
        int i;
        for(i = 1; i< noDuplicateList.size(); i++){
            if(noDuplicateList.get(i-1)-1 == noDuplicateList.get(i)) {
                straightCounter++;
            }else
                straightCounter = 0;
            if(straightCounter == 4)
                break;
        }
        if(straightCounter == 4)
            return CombinationEnum.STRAIGHT.calculateCombination(noDuplicateList.get(i-4));
        return 0;
    }

    private int calculateFourOfAKind() {
        for (int i = 3; i < Dice.diceRolledList.size(); i++) {
            if (Dice.diceRolledList.get(i-3).compareTo(Dice.diceRolledList.get(i)) == 0){
                return CombinationEnum.FOUR_OF_A_KIND.calculateCombination(Dice.diceRolledList.get(i));
            }
        }
        return 0;
    }

    private int calculateGenerala() {
        for (int i = 1; i < Dice.diceRolledList.size(); i++) {
            if (!(Dice.diceRolledList.get(i).compareTo(Dice.diceRolledList.get(0)) == 0))
                return 0;
        }
        return CombinationEnum.GENERALA.calculateCombination(Dice.diceRolledList.get(0));
    }
}
