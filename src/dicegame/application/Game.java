package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.Calculable;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private static Game gameInstance = null;

    private int rounds;
    private int playerCount;
    private int score = 0;
    private CombinationEnum playedCombination = null;
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

        List<Player> playerList = GameUtils.getInstance().fillPlayerList(playerCount);
        Dice dice = Dice.getInstance();

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        for(int round = 1; round<= rounds; round++) {
            System.out.println();

            for (Player player : playerList) {
                dice.resetDice();
                dice.rollDice();
                dice.sortDiceReverseOrder(Dice.getDiceRolled());
                evaluate(player,round);
                if (player.getPlayedCombinations().containsKey(CombinationEnum.GENERALA)) {
                    GameUtils.getInstance().endGame(playerList, player);
                    return;
                }
                System.out.println();
            }
        }
        GameUtils.getInstance().endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player player, int round) throws NullPointerException {
        int oldScore = player.getScore();
        List<Integer> list = new ArrayList<>(Dice.getDiceRolled());

        checkMethods();
        doSomethingMethod(player);

        if(score!= 0){
            player.getPlayedCombinations().put(playedCombination,score);
            player.setScore(player.getScore() + score);
            GameUtils.getInstance().printRound(player,
                    round,
                    oldScore,
                    score,
                    playedCombination.getLabel(),
                    list);
            return;
        }
        GameUtils.getInstance().printRound(player, round, oldScore, 0, "No Combination", list);
    }

    //checking for combinations

    private void checkMethods(){
        Dice.getSortedScore().put(CombinationEnum.PAIR,setPairDie());
        Dice.getSortedScore().put(CombinationEnum.DOUBLE_PAIR,returnDoublePairDie());
        Dice.getSortedScore().put(CombinationEnum.TRIPLE,returnTripleDie());
        //Dice.getSortedScore().put(CombinationEnum.TRIPLE,returnFullHouseRemainder());
        Dice.getSortedScore().put(CombinationEnum.FOUR_OF_A_KIND,returnFourOfAKindDie());
        Dice.getSortedScore().put(CombinationEnum.STRAIGHT,returnStraightFirstDie());
        Dice.getSortedScore().put(CombinationEnum.GENERALA,returnGeneralaDie());
    }

    private void doSomethingMethod(Player player){
        GameUtils.getInstance().sortByValue();
        for (Map.Entry<CombinationEnum, Integer> entry : Dice.getSortedScore().entrySet()){
            if(player.getPlayedCombinations().containsKey(entry.getKey()))
                continue;
            this.score = entry.getValue();
            this.playedCombination = entry.getKey();
            return;
        }
        this.score = 0;
        this.playedCombination = null;
    }

    //methods

    private int setPairDie() {
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-1).compareTo(Dice.getDiceRolled().get(i)) == 0) {
                return CombinationEnum.PAIR.calculateCombination(Dice.getDiceRolled().get(i));
            } }
        return 0;
    }

    private int returnDoublePairDie() {
        int pair1 = 0;
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-1).compareTo(Dice.getDiceRolled().get(i)) == 0) {
                if (pair1 == 0) {
                    pair1 = Dice.getDiceRolled().get(i);
                    i = ++i;
                    continue;
                }
                pair1 += Dice.getDiceRolled().get(i);
                return CombinationEnum.DOUBLE_PAIR.calculateCombination(pair1);
            }
        }
        return 0;
    }

    private int returnTripleDie() {
        for (int i = 2; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-2).compareTo(Dice.getDiceRolled().get(i)) == 0){
                return CombinationEnum.TRIPLE.calculateCombination(Dice.getDiceRolled().get(i));
            }
        }
        return 0;
    }

    private void returnFullHouseRemainder() {
    } //todo need to be fixed

    private int returnStraightFirstDie() {
        System.out.println("Rolled dice " + Dice.getDiceRolled());
        Set<Integer> diceRolledSet = new HashSet<>(Dice.getDiceRolled());
        List<Integer>listNoDublictaes = new ArrayList<>(diceRolledSet);
        Dice.getInstance().sortDiceReverseOrder(listNoDublictaes);
        System.out.println(listNoDublictaes);
        if(listNoDublictaes.size()<5)
            return 0;
        for(int i = 1; i< 5; i++){
            if(!(listNoDublictaes.get(i-1)-1 ==Dice.getDiceRolled().get(i))) {
                return 0;
            }
        }
        return CombinationEnum.STRAIGHT.calculateCombination(Dice.getDiceRolled().get(0));
    }

    private int returnFourOfAKindDie() {
        for (int i = 3; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-3).compareTo(Dice.getDiceRolled().get(i)) == 0){
                return CombinationEnum.FOUR_OF_A_KIND.calculateCombination(Dice.getDiceRolled().get(i));
            }
        }
        return 0;
    }

    private int returnGeneralaDie() {
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (!(Dice.getDiceRolled().get(i).compareTo(Dice.getDiceRolled().get(0)) == 0))
                return 0;
        }
        return CombinationEnum.GENERALA.calculateCombination(Dice.getDiceRolled().get(0));
    }
}
