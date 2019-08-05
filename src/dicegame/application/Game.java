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
                Dice.sortDiceReverseOrder(Dice.diceRolled);
                evaluate(player,round);
                if (player.getPlayedCombinations().containsKey(CombinationEnum.GENERALA)) {
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
            player.getPlayedCombinations().put(highestPlayedCombination,roundScore);
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

        score = calculatePair(Dice.diceRolled);
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.PAIR,score);

        score = calculateDoublePair();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.DOUBLE_PAIR,score);

        score = calculateTriple();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.TRIPLE,score);

        score = calculateFullHouse();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.FULL_HOUSE,score);

        score = calculateFourOfAKind();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.FOUR_OF_A_KIND,score);

        score = calculateStraight();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.STRAIGHT,score);

        score = calculateGenerala();
        if(score > 0)
            Dice.sortedScore.put(CombinationEnum.GENERALA,score);
    }

    private void findLargestCombination(Player player){
        GameUtils.sortByValue();
        for (Map.Entry<CombinationEnum, Integer> entry : Dice.sortedScore.entrySet()){
            if(player.getPlayedCombinations().containsKey(entry.getKey()))
                continue;
            this.roundScore = entry.getValue();
            this.highestPlayedCombination = entry.getKey();
            return;
        }
        this.roundScore = 0;
        this.highestPlayedCombination = null;
    }

    //methods

    private int calculatePair(List<Integer> rolledDice) {
        for (int i = 1; i < rolledDice.size(); i++) {
            if (Dice.diceRolled.get(i-1).compareTo(Dice.diceRolled.get(i)) == 0) {
                return CombinationEnum.PAIR.calculateCombination(Dice.diceRolled.get(i));
            } }
        return 0;
    }

    private int calculateDoublePair() {
        int bothPairs = 0;
        for (int i = 1; i < Dice.diceRolled.size(); i++) {
            if (Dice.diceRolled.get(i-1).compareTo(Dice.diceRolled.get(i)) == 0) {
                if (bothPairs == 0) {
                    bothPairs = Dice.diceRolled.get(i);
                    i = ++i;
                    continue;
                }
                bothPairs += Dice.diceRolled.get(i);
                return CombinationEnum.DOUBLE_PAIR.calculateCombination(bothPairs);
            }
        }
        return 0;
    }

    private int calculateTriple() {
        for (int i = 2; i < Dice.diceRolled.size(); i++) {
            if (Dice.diceRolled.get(i-2).compareTo(Dice.diceRolled.get(i)) == 0){
                return CombinationEnum.TRIPLE.calculateCombination(Dice.diceRolled.get(i));
            }
        }
        return 0;
    }

    private int calculateFullHouse() {
        List<Integer> sortedListWithoutTriple = new ArrayList<>(Dice.diceRolled);
        int tripleDie = (calculateTriple()-CombinationEnum.TRIPLE.getValue())/3;
        sortedListWithoutTriple.removeAll(Collections.singleton(tripleDie));
        int pairDie = (calculatePair(sortedListWithoutTriple)-CombinationEnum.PAIR.getValue())/2;

        if(tripleDie <= 0 || pairDie <= 0)
            return 0;
        return CombinationEnum.FULL_HOUSE.calculateCombination((3*tripleDie)+(2*pairDie));
    }

    private int calculateStraight() {
        Set<Integer> diceRolledSet = new HashSet<>(Dice.diceRolled);
        List<Integer>noDuplicateList = new ArrayList<>(diceRolledSet);
        Dice.sortDiceReverseOrder(noDuplicateList);

        if(noDuplicateList.size()<5)
            return 0;
        for(int i = 1; i< 5; i++){
            if(!(noDuplicateList.get(i-1)-1 == noDuplicateList.get(i))) {
                return 0;
            }
        }
        return CombinationEnum.STRAIGHT.calculateCombination(noDuplicateList.get(0));
    }

    private int calculateFourOfAKind() {
        for (int i = 3; i < Dice.diceRolled.size(); i++) {
            if (Dice.diceRolled.get(i-3).compareTo(Dice.diceRolled.get(i)) == 0){
                return CombinationEnum.FOUR_OF_A_KIND.calculateCombination(Dice.diceRolled.get(i));
            }
        }
        return 0;
    }

    private int calculateGenerala() {
        for (int i = 1; i < Dice.diceRolled.size(); i++) {
            if (!(Dice.diceRolled.get(i).compareTo(Dice.diceRolled.get(0)) == 0))
                return 0;
        }
        return CombinationEnum.GENERALA.calculateCombination(Dice.diceRolled.get(0));
    }
}
