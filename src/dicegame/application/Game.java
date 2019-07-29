package dicegame.application;

import dicegame.elements.Combinations;
import dicegame.GameUtils;
import dicegame.elements.Player;

import java.util.*;

class Game extends GameUtils {
    private int rounds;
    private int players;

    Game(int rounds, int players) {
        this.rounds = rounds;
        this.players = players;
    }

    static private Random random = new Random();
    private static final int NUMBER_OF_DICES = 5 ;

    private GameUtils gameUtils = GameUtils.getInstance();

    void playGame(){
        System.out.println(">>> WELCOME TO THE DICE GAME <<<");

        int  r =  rounds;

        List<Player> playerList = gameUtils.fillPlayerList(players);

        while(rounds-->0){
            System.out.println();
            for(Player p: playerList){
                resetDice(p);
                rollDice(p);
                sortDice(p);
                evaluate(p,(-(rounds-r)));

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

                    gameUtils.printRound(p,round,oldScore,entryValue,Combinations.getTypeCombination()[entryKey]);
                    return;
                }
            }else
                break;
        }
        gameUtils.printRound(p,round,oldScore,0,"No Combination");
    }

    //dice Methods

    private void rollDice(Player player){
        for(int i = 0;i< NUMBER_OF_DICES;i++){
            player.getDiceRolled().add(i, random.nextInt(6)+1);
        }
    }

    private void resetDice(Player player){
        player.getDiceRolled().clear();
    }

    private void sortDice(Player player){
        player.getDiceRolled().sort(Comparator.naturalOrder());
    }

    //checking for combinations

    private int checkPair(List<Integer> diceRolled){
        int maxScore = 0;

        for(int i = 0, j = 1; j< diceRolled.size();i++, j++){
            if(diceRolled.get(i).compareTo(diceRolled.get(j))==0){
                if(Combinations.pair(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.pair(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkDoublePair(List<Integer> diceRolled){
        int pair1 = 0, pair2 = 0;
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
                if(pair1 > 0 && pair2> 0 && Combinations.doublePair(pair1,pair2) > maxScore)
                    maxScore = Combinations.doublePair(pair1,pair2);
            }
        }
        return maxScore;
    }

    private int checkTriple(List<Integer> diceRolled){
        int maxScore = 0;

        for(int i = 0, j = 1,k =2; k< diceRolled.size();i++, j++, k++){
            if(diceRolled.get(i).compareTo(diceRolled.get(j))==0
                    && diceRolled.get(k).compareTo(diceRolled.get(j))==0){
                if(Combinations.pair(diceRolled.get(i)) > maxScore)
                    maxScore = Combinations.triple(diceRolled.get(i));
            }
        }
        return maxScore;
    }

    private int checkFullHouse(List<Integer> diceRolled){
        int maxScore = 0;
        if(diceRolled.get(0).compareTo(diceRolled.get(1))==0
                &&(diceRolled.get(2).compareTo(diceRolled.get(3))==0
                && diceRolled.get(3).compareTo(diceRolled.get(4))==0)){
            maxScore = Combinations.fullHouse(diceRolled.get(0),diceRolled.get(4));
        }
        if(diceRolled.get(3).compareTo(diceRolled.get(4))==0
                &&(diceRolled.get(0).compareTo(diceRolled.get(1))==0
                && diceRolled.get(1).compareTo(diceRolled.get(2))==0)){
            maxScore = Combinations.fullHouse(diceRolled.get(4),diceRolled.get(0));
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
        return Combinations.straight(diceRolled.get(4) == 5);
    }

    private int checkFourOfaKind(List<Integer> diceRolled){
        if(diceRolled.get(0).compareTo(diceRolled.get(3))==0)
            return Combinations.fourOfaKind(diceRolled.get(0));
        if(diceRolled.get(1).compareTo(diceRolled.get(4))==0)
            return Combinations.fourOfaKind(diceRolled.get(1));
        return 0;
    }

    private int checkGenerala(List<Integer> diceRolled){
        for(int i= 1;i< diceRolled.size();i++){
            if(!(diceRolled.get(i).compareTo(diceRolled.get(0))==0)){
                return 0;
            }
        }
        return Combinations.generala(diceRolled.get(0));
    }

}
