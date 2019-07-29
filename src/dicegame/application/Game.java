package dicegame.application;

import dicegame.elements.Combinations;
import dicegame.GameUtils;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.util.*;

class Game extends GameUtils {
    private int roundsLeft;
    private final int playerCount;

    Game(int rounds, int playerCount) {
        this.roundsLeft = rounds;
        this.playerCount = playerCount;
    }

    private GameUtils gameUtils = GameUtils.getInstance();

    void playGame(){
        int  initialRounds =  roundsLeft;
        List<Player> playerList = gameUtils.fillPlayerList(playerCount);
        Dice dice = Dice.getInstance();

        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        while(roundsLeft-->0){
            System.out.println();
            for(Player p: playerList){
                dice.resetDice(p);
                dice.rollDice(p);
                dice.sortDice(p);
                evaluate(p,(-(roundsLeft-initialRounds)));

                if (p.getPlayedCombinations()[6]) {
                    gameUtils.endGame(playerList,p);
                    break;
                }
                System.out.println();
            }
        }
        gameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player p, int round){
        int oldScore = p.getScore();
        int entryKey;
        int entryValue;

        Player.getSortScore().put(0,checkPair(p.getDiceRolled()));

        Player.getSortScore().put(1,checkDoublePair(p.getDiceRolled()));

        Player.getSortScore().put(2,checkTriple(p.getDiceRolled()));

        Player.getSortScore().put(3,checkFullHouse(p.getDiceRolled()));

        Player.getSortScore().put(4,checkStraight(p.getDiceRolled()));

        Player.getSortScore().put(5,checkFourOfaKind(p.getDiceRolled()));

        Player.getSortScore().put(6,checkGenerala(p.getDiceRolled()));

        Player.setSortScore(gameUtils.sortByValue());

        for(int i = 0; i< Combinations.getTypeCombination().length; i++) {
            entryKey = Objects.requireNonNull(gameUtils.getEntry(i)).getKey();
            entryValue = Objects.requireNonNull(gameUtils.getEntry(i)).getValue();

            if (entryValue> 0) {
                if (!p.getPlayedCombinations()[entryKey]) {
                    p.getPlayedCombinations()[entryKey] = true;
                    p.setScore(p.getScore() + entryValue);

                    gameUtils.printRound(p,round,oldScore,entryValue,Combinations.getTypeCombination()[entryKey].getLabel());
                    return;
                }
            }else
                break;
        }
        gameUtils.printRound(p,round,oldScore,0,"No Combination");
    }

    //checking for combinations

    private int checkPair(List<Integer> diceRolled){
        int maxScore = 0;

        for(int i = 0, j = 1; j< diceRolled.size();i++, j++){
            if(diceRolled.get(i).compareTo(diceRolled.get(j))==0){
                if(Combinations.calcuatePair(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.calcuatePair(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkDoublePair(List<Integer> diceRolled){
        int pair1 = 0, pair2;
        int maxScore = 0;

        for(int i = 0, j =1; j< diceRolled.size(); i++, j++){
            if(diceRolled.get(i).compareTo(diceRolled.get(j))==0){
                if(pair1 == 0){
                    pair1 = diceRolled.get(i);
                    i = ++i;
                    j = ++j;
                    continue;
                }
                pair2 = diceRolled.get(i);
                if(pair1 > 0
                        && pair2> 0
                        && Combinations.calcuateDoublePair(pair1,pair2) > maxScore)
                    maxScore = Combinations.calcuateDoublePair(pair1,pair2);
            }
        }
        return maxScore;
    }

    private int checkTriple(List<Integer> diceRolled){
        int maxScore = 0;

        for(int i = 0, j = 1,k =2; k< diceRolled.size();i++, j++, k++){
            if(diceRolled.get(i).compareTo(diceRolled.get(j))==0
                    && diceRolled.get(k).compareTo(diceRolled.get(j))==0){
                if(Combinations.calcuateTriple(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.calcuateTriple(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkFullHouse(List<Integer> diceRolled){
        int maxScore = 0;
        if(diceRolled.get(0).compareTo(diceRolled.get(1))==0
                &&(diceRolled.get(2).compareTo(diceRolled.get(3))==0
                && diceRolled.get(3).compareTo(diceRolled.get(4))==0)){
            maxScore = Combinations.calcuateFullHouse(diceRolled.get(0),diceRolled.get(4));
        }
        if(diceRolled.get(3).compareTo(diceRolled.get(4))==0
                &&(diceRolled.get(0).compareTo(diceRolled.get(1))==0
                && diceRolled.get(1).compareTo(diceRolled.get(2))==0)){
            maxScore = Combinations.calcuateFullHouse(diceRolled.get(4),diceRolled.get(0));
        }
        return maxScore;
    }

    private int checkStraight(List<Integer> diceRolled){
        int i = 1;
        int j = 0;
        if(diceRolled.get(0)== 2) {
            j = 1;
            i = 2;
        }
        for(;i<diceRolled.size(); i++){
            if(!(diceRolled.get(i)== (i+1+j)))
                return 0;
        }
        return Combinations.calcuateStraight(diceRolled.get(4) == 5);
    }

    private int checkFourOfaKind(List<Integer> diceRolled){
        if(diceRolled.get(0).compareTo(diceRolled.get(3))==0)
            return Combinations.calcuateFourOfAKind(diceRolled.get(0));
        if(diceRolled.get(1).compareTo(diceRolled.get(4))==0)
            return Combinations.calcuateFourOfAKind(diceRolled.get(1));
        return 0;
    }

    private int checkGenerala(List<Integer> diceRolled){
        for(int i= 1;i< diceRolled.size();i++){
            if(!(diceRolled.get(i).compareTo(diceRolled.get(0))==0)){
                return 0;
            }
        }
        return Combinations.calcuateGenerala(diceRolled.get(0));
    }
}
