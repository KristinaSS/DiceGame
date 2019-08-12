package dicegame.elements;

import dicegame.enums.CombinationEnum;

import java.util.Map;
import static dicegame.elements.DiceRolled.findFirstValueGreaterThanOrEqualTo;

public class Round {
    private Player player;
    private int tempScore;
    private int maxCurRoundCurPlayerScore;
    private CombinationEnum comboForMaxScore;

    public Round(Player player) {
        this.player = player;
        this.tempScore = 0;
        this.maxCurRoundCurPlayerScore = 0;
        this.comboForMaxScore = CombinationEnum.NO_COMBINATION;
    }

    //public methods

    public void updatePlayerAndPrintPlayerRound(int round) {
        int oldScore = player.getScore();

        callMethodsForUpdatingPlayerRoundScore();

        if (maxCurRoundCurPlayerScore > 0) {
            player.getPlayedCombinationsSet().add(comboForMaxScore);
            player.setScore(player.getScore() + maxCurRoundCurPlayerScore);
            printRound(round, oldScore);
            return;
        }
        printRound(round, oldScore);
    }

    //caller methods

    private void callMethodsForUpdatingPlayerRoundScore() {

        updateRoundScoreIfGeneralaValid();

        if (player.getPlayedCombinationsSet().size() >= CombinationEnum.values().length - 1
                || maxCurRoundCurPlayerScore >0 )
            return;

        updateRoundScoreIfStraightValid();

        int fourOfAKind = getFourOfAKind();
        int triple = getTriple(fourOfAKind);
        int pair = getPair(triple);
        int secondPair = getSecondPair(pair);

        updateRoundScoreIfFourOfAKindValid(fourOfAKind);

        updateRoundScoreIfTripleValid(triple);

        updateRoundScoreIfFullHouseValid(triple, pair);

        updateRoundScoreIfPairValid(triple, pair);

        updateRoundScoreIfDoublePairValid(pair, secondPair);
    }

    private void printRound(int round, int oldScore) {

        System.out.println(">>> round: " + round);
        System.out.println(">player " + player.getPlayerNumber() + ":");
        System.out.println("current score: " + oldScore);
        System.out.println("dice roll:" + DiceRolled.rolledDiceListToString() +
                "-> " + comboForMaxScore.getLabel() +
                " (" + maxCurRoundCurPlayerScore + ") ");
        System.out.println("new score: " + player.getScore());
        System.out.println();
    }

    //Updating score and getting combo die numbers

    private void updateRoundScoreIfGeneralaValid(){
        CombinationEnum generala = CombinationEnum.GENERALA;
        if (DiceRolled.getTimesRepeatedEachDice().size() != 1)
            return;

        if(!player.getPlayedCombinationsSet().contains(generala)){
            comboForMaxScore = generala;
            maxCurRoundCurPlayerScore = generala
                    .calculateCombination(findFirstValueGreaterThanOrEqualTo
                            (generala.getDiceCount()));
        }
    }

    private void updateRoundScoreIfStraightValid() {
        int straightCounter = 0;
        int beginningOfStraight = 0;
        CombinationEnum straight = CombinationEnum.STRAIGHT;
        Map<Integer, Integer> timesRepeatedEachDieSideMap = DiceRolled.getTimesRepeatedEachDice();

        if (timesRepeatedEachDieSideMap.size() < straight.getDiceCount()) //if mapsize == 5
            return;

        if(player.getPlayedCombinationsSet().contains(straight)) //if player has already played straight
            return;

        //System.out.println("Enter straight");
        int i;
        for (i = 0; i < Die.numberOfSides; i++) {
            if (timesRepeatedEachDieSideMap.containsKey(Die.numberOfSides - i)) {
                if (straightCounter == 0){
                    beginningOfStraight = Die.numberOfSides - i;}
                straightCounter++;
            } else
                straightCounter = 0;
            //System.out.println("straigth counter "+ straightCounter);
            if (straightCounter == straight.getDiceCount()) {

                tempScore = straight.calculateCombination(beginningOfStraight);

                if (tempScore > this.maxCurRoundCurPlayerScore) {
                    this.maxCurRoundCurPlayerScore = tempScore;
                    this.comboForMaxScore = straight;
                    return;
                }
            }
        }
    }

    private void updateRoundScoreIfFourOfAKindValid(int foundFourOfAKind){
        CombinationEnum fourOfAKind = CombinationEnum.FOUR_OF_A_KIND;
        if (foundFourOfAKind <= 0)
            return;
        if(player.getPlayedCombinationsSet().contains(fourOfAKind))
            return;

        tempScore = fourOfAKind.calculateCombination(foundFourOfAKind);

        if (tempScore > this.maxCurRoundCurPlayerScore) {
            this.maxCurRoundCurPlayerScore = tempScore;
            this.comboForMaxScore = fourOfAKind;
        }
    }

    private void updateRoundScoreIfTripleValid(int tripleDie){
        CombinationEnum triple = CombinationEnum.TRIPLE;
        if (tripleDie <= 0)
            return;
        if (player.getPlayedCombinationsSet().contains(triple))
            return;
        tempScore = triple.calculateCombination(tripleDie);

        if (tempScore > this.maxCurRoundCurPlayerScore) {
            this.maxCurRoundCurPlayerScore = tempScore;
            this.comboForMaxScore = triple;
        }
    }

    private void updateRoundScoreIfFullHouseValid(int triple, int pair) {
        CombinationEnum fullHouse = CombinationEnum.FULL_HOUSE;
        if (triple <= 0 || pair <= 0)
            return;
        if(player.getPlayedCombinationsSet().contains(fullHouse))
            return;

        tempScore = fullHouse.calculateCombination((3 * triple) + (2 * pair));

        if (tempScore > this.maxCurRoundCurPlayerScore) {
            this.maxCurRoundCurPlayerScore = tempScore;
            this.comboForMaxScore = fullHouse;
        }
    }

    private void updateRoundScoreIfPairValid(int tripleDie, int pairDie) {
        CombinationEnum pair = CombinationEnum.PAIR;
        if (player.getPlayedCombinationsSet().contains(pair))
            return;
        if (tripleDie > 0 && tripleDie > pairDie) {
            tempScore = pair.calculateCombination(tripleDie);
            if (tempScore > this.maxCurRoundCurPlayerScore)
                this.maxCurRoundCurPlayerScore = tempScore;
        } else if (pairDie > 0) {
            tempScore = pair.calculateCombination(pairDie);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = pair;
            }
        }
    }

    private void updateRoundScoreIfDoublePairValid(int pair, int secondPair) {
        CombinationEnum doublePair = CombinationEnum.DOUBLE_PAIR;
        if (secondPair > 0 && !(player.getPlayedCombinationsSet().contains(doublePair))) {
            tempScore = doublePair.calculateCombination(pair + secondPair);
            if (tempScore > this.maxCurRoundCurPlayerScore) {
                this.maxCurRoundCurPlayerScore = tempScore;
                this.comboForMaxScore = doublePair;
            }
        }
    }

    private int getFourOfAKind() {
        System.out.println(CombinationEnum.FOUR_OF_A_KIND.getDiceCount());
        return findFirstValueGreaterThanOrEqualTo(CombinationEnum.FOUR_OF_A_KIND.getDiceCount());
    }

    private int getTriple(int fourOfAKind) {
        int triple = findFirstValueGreaterThanOrEqualTo(CombinationEnum.TRIPLE.getDiceCount());
        if (fourOfAKind > triple)
            triple = fourOfAKind;
        return triple;
    }

    private int getPair(int triple){
        DiceRolled.getTimesRepeatedEachDice().remove(triple);
        return findFirstValueGreaterThanOrEqualTo(CombinationEnum.PAIR.getDiceCount());
    }

    private int getSecondPair(int pair) {
        if (pair > 0) {
            DiceRolled.getTimesRepeatedEachDice().remove(pair);
            return findFirstValueGreaterThanOrEqualTo(CombinationEnum.DOUBLE_PAIR.getDiceCount());
        }
        return -1;
    }
}
