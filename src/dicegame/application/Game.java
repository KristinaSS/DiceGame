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
        int oldScore = player.getScore();
        CombinationEnum entryKey;
        int entryValue;

        Dice.setSortScore(GameUtils.getInstance().sortByValue());



        for (int i = 0; i < CombinationEnum.values().length; i++) {
            entryKey = GameUtils.getInstance().getEntry(i).getKey();
            entryValue = GameUtils.getInstance().getEntry(i).getValue();
            if (entryValue > 0) {
                if (!player.getPlayedCombinations().contains(entryKey)){

                    player.getPlayedCombinations().add(entryKey);
                    player.setScore(player.getScore() + entryValue);

                    GameUtils.getInstance().printRound(player,
                            round,
                            oldScore,
                            entryValue,
                            entryKey.getLabel());
                    return;
                }
            } else
                break;
        }
        GameUtils.getInstance().printRound(player, round, oldScore, 0, "No Combination");
    }

    //checking for combinations

    private void checkMethods(List<Integer> diceRolled){


    }

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
                CombinationEnum.TRIPLE.setDieNumber(i);
                return Dice.getDiceRolled().get(i);
            }
        }
        CombinationEnum.TRIPLE.setDieNumber(0);
        return 0;
    }

    private int returnFullHouseRemainder() {
        if(CombinationEnum.TRIPLE.getDieNumber()> 0) {
            CombinationEnum.FULL_HOUSE.setDieNumber(0);
            if(CombinationEnum.FULL_HOUSE.getDieNumber()>0){
                return 1;
            }
        }
        return 0;
    }

    private void returnStraightFirstDie() {
        for(int i = 1; i< Dice.getInstance().getNumberOfDice(); i++){
            if(!(Dice.getDiceRolled().get(i-1)-1 ==Dice.getDiceRolled().get(i))) {
                CombinationEnum.STRAIGHT.setDieNumber(Dice.getDiceRolled().get(i));
                return;
            }
        }
        CombinationEnum.STRAIGHT.setDieNumber(0);
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
