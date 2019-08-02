package dicegame.elements;


import dicegame.application.Game;

import java.util.Collections;
import java.util.List;

public enum CombinationEnum implements Calculable {

    GENERALA(0, 50, "Generala"){
        int dieNumber;

        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer>dieNumbers) {
            return (5 * dieNumber) + this.getValue();
        }
    },
    STRAIGHT(1, 30, "Straight"){
        int dieNumber;

        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        @Override
        public int getDieNumber() {
            return dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer>dieNumbers) {
            int score = 0;
            for (int i = 0; i < Dice.getInstance().getNumberOfDice(); i++) {
                score += (dieNumber + i);
            }
            return score;
        }
    },
    FOUR_OF_A_KIND(2, 40, "Four of a Kind"){
        int dieNumber;

        @Override
        public int getDieNumber() {
            return dieNumber;
        }

        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer>dieNumbers) {
            return (4 * dieNumber) + this.getValue();
        }
    },
    DOUBLE_PAIR(3, 15, "Double Pair"){
        int dieNumber;

        @Override
        public int getDieNumber() {
            return dieNumber;
        }

        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer>dieNumbers) {
            return (dieNumber* 2) + this.getValue();
        }
    },
    PAIR(4, 10, "Pair"){
        int dieNumber;

        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        public int getDieNumber() {
            return dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer> dieNumbers) {
            return (dieNumber * 2) + this.getValue();
        }
    },
    TRIPLE(5, 20, "Triple"){
        int dieNumber;

        @Override
        public int getDieNumber() {
            return dieNumber;
        }
        @Override
        public void setDieNumber(int dieNumber) {
            this.dieNumber = dieNumber;
        }

        @Override
        public int calculateCombination(List<Integer>dieNumbers) {
            return (3 * dieNumber) + this.getValue();
        }
    },
    FULL_HOUSE(6, 25, "Full House"){
        int tripleDie;
        int pairDie ;


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
        public int calculateCombination(List<Integer>dieNumbers) {
            return (2 * pairDie) + (3 * tripleDie) + this.getValue();
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
