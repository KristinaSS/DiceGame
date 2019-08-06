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
                System.out.println();
            }
        }
        GameUtils.endGame(playerList, null);
    }

    //evaluation

    private void evaluate(Player player, int round) throws NullPointerException {
        int oldScore = player.getScore();

        //fillSortedMap(player);
        if(roundScore > 0)
           // findLargestCombination(player);

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

/*    //checking for combinations

    private void fillSortedMap(Player player){
        int score;
        score = calculateGenerala();

        if(player.getPlayedCombinationsMap().size()>= CombinationEnum.values().length-1
                    && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.GENERALA))){
            this.highestPlayedCombination = CombinationEnum.GENERALA;
            this.roundScore = score;
            return;
        }else {
            if (score > 0)
                Dice.sortedScoreMap.put(CombinationEnum.GENERALA, score);
        }
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
    }*/
    public void method(Player player){
        if(Dice.listTemp.size() == 1){
            //Dice.sortedScoreMap.put(CombinationEnum.GENERALA, CombinationEnum.GENERALA.calculateCombination(Dice.listTemp.;
            return;
        }
        int temp = stramMap(4);

        if(temp > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.FOUR_OF_A_KIND))) {
            Dice.sortedScoreMap.put(CombinationEnum.FOUR_OF_A_KIND,
                    CombinationEnum.FOUR_OF_A_KIND.calculateCombination(temp));
        }
        int triple = stramMap(3);
        Dice.listTemp.remove(temp);
        int pair = stramMap(2);

        if(triple>0 && pair>0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.FULL_HOUSE)))
            Dice.sortedScoreMap.put(CombinationEnum.FULL_HOUSE,0/*calculateFullHouse()*/);

        if(triple>0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.TRIPLE)))
            Dice.sortedScoreMap.put(CombinationEnum.FULL_HOUSE,0/*calculateFullHouse()*/);

        if(triple>0 && triple > pair && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.PAIR)))
            Dice.sortedScoreMap.put(CombinationEnum.PAIR,0/*calculatePair(new List<Integer>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<Integer> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean add(Integer integer) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends Integer> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends Integer> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public Integer get(int index) {
                    return null;
                }

                @Override
                public Integer set(int index, Integer element) {
                    return null;
                }

                @Override
                public void add(int index, Integer element) {

                }

                @Override
                public Integer remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<Integer> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<Integer> listIterator(int index) {
                    return null;
                }

                @Override
                public List<Integer> subList(int fromIndex, int toIndex) {
                    return null;
                }
            })*/);
        else if(pair > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.PAIR)))
//            Dice.sortedScoreMap.put(CombinationEnum.PAIR,0calculatePair(new List<Integer>() {
//                @Override
//                public int size() {
//                    return 0;
//                }
//
//                @Override
//                public boolean isEmpty() {
//                    return false;
//                }
//
//                @Override
//                public boolean contains(Object o) {
//                    return false;
//                }
//
//                @Override
//                public Iterator<Integer> iterator() {
//                    return null;
//                }
//
//                @Override
//                public Object[] toArray() {
//                    return new Object[0];
//                }
//
//                @Override
//                public <T> T[] toArray(T[] a) {
//                    return null;
//                }
//
//                @Override
//                public boolean add(Integer integer) {
//                    return false;
//                }
//
//                @Override
//                public boolean remove(Object o) {
//                    return false;
//                }
//
//                @Override
//                public boolean containsAll(Collection<?> c) {
//                    return false;
//                }
//
//                @Override
//                public boolean addAll(Collection<? extends Integer> c) {
//                    return false;
//                }
//
//                @Override
//                public boolean addAll(int index, Collection<? extends Integer> c) {
//                    return false;
//                }
//
//                @Override
//                public boolean removeAll(Collection<?> c) {
//                    return false;
//                }
//
//                @Override
//                public boolean retainAll(Collection<?> c) {
//                    return false;
//                }
//
//                @Override
//                public void clear() {
//
//                }
//
//                @Override
//                public Integer get(int index) {
//                    return null;
//                }
//
//                @Override
//                public Integer set(int index, Integer element) {
//                    return null;
//                }
//
//                @Override
//                public void add(int index, Integer element) {
//
//                }
//
//                @Override
//                public Integer remove(int index) {
//                    return null;
//                }
//
//                @Override
//                public int indexOf(Object o) {
//                    return 0;
//                }
//
//                @Override
//                public int lastIndexOf(Object o) {
//                    return 0;
//                }
//
//                @Override
//                public ListIterator<Integer> listIterator() {
//                    return null;
//                }
//
//                @Override
//                public ListIterator<Integer> listIterator(int index) {
//                    return null;
//                }
//
//                @Override
//                public List<Integer> subList(int fromIndex, int toIndex) {
//                    return null;
//                }
//            }));
        if(pair > 0){
            Dice.listTemp.remove(pair);
            int doublePair = stramMap(2);
            if(doublePair > 0 && !(player.getPlayedCombinationsMap().containsKey(CombinationEnum.DOUBLE_PAIR)))
                Dice.sortedScoreMap.put(CombinationEnum.DOUBLE_PAIR,0/*calculateDoublePair()*/);

        }

    }
    public int stramMap (int compareBy){
        List<Integer> temp;
        temp = Dice.listTemp.entrySet().stream()
                .filter(entry -> entry.getValue() > compareBy)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        Dice.sortDiceReverseOrder(temp);
        return temp.size()>0?temp.get(0):-1;
    }
}
