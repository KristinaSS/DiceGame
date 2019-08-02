package dicegame.elements;


import dicegame.application.Game;

import java.util.Collections;

public enum CombinationEnum implements Calculable {

    GENERALA(0, 50, "Generala"){
        int dieNumber =0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            score = (5 * dieNumber) + this.getValue();
        }
    },
    STRAIGHT(1, 30, "Straight"){
        int dieNumber =0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            int score = 0;
            for (int i = 0; i < Dice.getInstance().getNumberOfDice(); i++) {
                score += (dieNumber - i);
            }
            this.score = score;
        }
    },
    FOUR_OF_A_KIND(2, 40, "Four of a Kind"){
        int dieNumber = 0;
        int score;
        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            score = (4 * dieNumber) + this.getValue();
        }
    },
    DOUBLE_PAIR(3, 15, "Double Pair"){
        int dieNumber =0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            score = (dieNumber* 2) + this.getValue();
        }
    },
    PAIR(4, 10, "Pair"){
        int dieNumber=0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            score = (dieNumber * 2) + this.getValue();
        }
    },
    TRIPLE(5, 20, "Triple"){
        int dieNumber = 0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            //System.out.println("triple set die number "+ dieNumber);
            this.dieNumber = dieNumber;
        }
        @Override
        public void calculateCombination() {
            if(dieNumber == 0)
                return;
            //System.out.println("triple "+ dieNumber);
            score= (3 * dieNumber) + this.getValue();
        }
    },
    FULL_HOUSE(6, 25, "Full House"){
        int tripleDie;
        int pairDie = 0;
        int score;

        @Override
        public int getScore() {
            return score;
        }
        @Override
        public void setScore(int score) {
            this.score = score;
        }
        @Override
        public void setDieNumber(int i) {
            this.tripleDie = Game.getInstance().returnTripleDie();
            Dice.getDiceRolled().removeAll(Collections.singleton(tripleDie));
            this.pairDie = Game.getInstance().setPairDie();
        }
        @Override
        public int getDieNumber() {
            return pairDie;
        }
        @Override
        public void calculateCombination() {
            if(pairDie == 0)
                return;
            score = (2 * pairDie) + (3 * tripleDie) + this.getValue();
        }
    };

    int index;
    int combinationValue;
    String label;

    CombinationEnum(int index, int value, String label) {
        this.index = index;
        this.label = label;
        combinationValue = value;
    }

    public int getValue() {
        return combinationValue;
    }

    public String getLabel() throws NullPointerException {
        return label;
    }

    public int getIndex() {
        return index;
    }
}
