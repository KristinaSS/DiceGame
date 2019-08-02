package dicegame.application;

import dicegame.GameUtils;
import dicegame.elements.Calculable;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;
import java.util.stream.Collectors;

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
                dice.sortDiceReverseOrder();
                evaluate(player,round);

                if (player.getPlayedCombinations().contains(CombinationEnum.GENERALA)) {
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
        //System.out.println("new");
        int oldScore = player.getScore();
        List<Integer> list = Dice.getDiceRolled();

        List<CombinationEnum> combinationEnums = new ArrayList<>();

        checkMethods();
        doSomethingMethod(player, combinationEnums);

        combinationEnums.sort((o1, o2) -> Integer.compare(o2.getScore(),o1.getScore()));

        for(CombinationEnum combinationEnum: combinationEnums){
            System.out.println(combinationEnum.getLabel()+" score " + combinationEnum.getScore());}

        if(combinationEnums.size()>0){
            player.getPlayedCombinations().add(combinationEnums.get(0));
            player.setScore(player.getScore() + combinationEnums.get(0).getScore());
            GameUtils.getInstance().printRound(player,
                    round,
                    oldScore,
                    combinationEnums.get(0).getScore(),
                    combinationEnums.get(0).getLabel(),
                    list);
            for(CombinationEnum combinationEnum: CombinationEnum.values()){
                combinationEnum.setScore(0);
                combinationEnum.setDieNumber(0);
            }
            return;
        }
        GameUtils.getInstance().printRound(player, round, oldScore, 0, "No Combination", list);

        for(CombinationEnum combinationEnum: CombinationEnum.values()){
            combinationEnum.setScore(0);
            combinationEnum.setDieNumber(0);
        }
    }

    //checking for combinations

    private void checkMethods(){
        returnGeneralaDie();
        returnStraightFirstDie();
        returnFourOfAKindDie();
        setPairDie();
        returnDoublePairDie();
        returnTripleDie();
        returnFullHouseRemainder();
    }

    private void doSomethingMethod(Player player, List<CombinationEnum>combinationEnumList){
        for(CombinationEnum combinationEnum: CombinationEnum.values()){
            combinationEnum.calculateCombination();
            if(player.getPlayedCombinations().contains(combinationEnum))
                continue;
            if(combinationEnum.getScore()>0)
                combinationEnumList.add(combinationEnum);
        }
    }

    //methods

    public int setPairDie() {
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-1).compareTo(Dice.getDiceRolled().get(i)) == 0) {
                CombinationEnum.PAIR.setDieNumber(Dice.getDiceRolled().get(i));
                return Dice.getDiceRolled().get(i);
            } }
        CombinationEnum.PAIR.setDieNumber(0);
        return 0;
    }

    private void returnDoublePairDie() {
        int pair1 = 0;
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-1).compareTo(Dice.getDiceRolled().get(i)) == 0) {
                if (pair1 == 0) {
                    pair1 = Dice.getDiceRolled().get(i);
                    i = ++i;
                    continue;
                }
                pair1 += Dice.getDiceRolled().get(i);
                CombinationEnum.DOUBLE_PAIR.setDieNumber(pair1);
                return;
            }
        }
        CombinationEnum.DOUBLE_PAIR.setDieNumber(0);
    }

    public int returnTripleDie() {
        for (int i = 2; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-2).compareTo(Dice.getDiceRolled().get(i)) == 0){
                CombinationEnum.TRIPLE.setDieNumber(Dice.getDiceRolled().get(i));
                return Dice.getDiceRolled().get(i);
            }
        }
        CombinationEnum.TRIPLE.setDieNumber(0);
        return 0;
    }

    private void returnFullHouseRemainder() {
        //if(CombinationEnum.TRIPLE.getDieNumber()> 0) {
            CombinationEnum.FULL_HOUSE.setDieNumber(0);
       // }
    }

    private void returnStraightFirstDie() {
        for(int i = 1; i< Dice.getInstance().getNumberOfDice(); i++){
            if(!(Dice.getDiceRolled().get(i-1)-1 ==Dice.getDiceRolled().get(i))) {
                CombinationEnum.STRAIGHT.setDieNumber(0);
                return;
            }
        }
        CombinationEnum.STRAIGHT.setDieNumber(Dice.getDiceRolled().get(0));
    }

    private void returnFourOfAKindDie() {
        for (int i = 3; i < Dice.getDiceRolled().size(); i++) {
            if (Dice.getDiceRolled().get(i-3).compareTo(Dice.getDiceRolled().get(i)) == 0){
                CombinationEnum.FOUR_OF_A_KIND.setDieNumber(i);
                return;            }
        }
        CombinationEnum.FOUR_OF_A_KIND.setDieNumber(0);
    }

    private void returnGeneralaDie() {
        for (int i = 1; i < Dice.getDiceRolled().size(); i++) {
            if (!(Dice.getDiceRolled().get(i).compareTo(Dice.getDiceRolled().get(0)) == 0))
                CombinationEnum.GENERALA.setDieNumber(0);
        }
        CombinationEnum.TRIPLE.setDieNumber(Dice.getDiceRolled().get(0));
    }
}
