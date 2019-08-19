package dicegame.constants;

import dicegame.elements.DiceRolled;
import dicegame.interfaces.Calculable;

public enum CombinationEnum implements Calculable {
    PAIR(10, "Pair", 2) {
        @Override
        public int calculateCombination(int dieNumber) {
            return (dieNumber * 2) + this.getValue();
        }
    },
    TRIPLE(20, "Triple", 3) {
        @Override
        public int calculateCombination(int dieNumber) {
            return (3 * dieNumber) + this.getValue();
        }
    },
    DOUBLE_PAIR(15, "Double Pair", 2) {
        @Override
        public int calculateCombination(int dieNumber) {
            return (dieNumber * 2) + this.getValue();
        }
    },
    FULL_HOUSE(25, "Full House", 1) {
        @Override
        public int calculateCombination(int dieNumber) {
            return dieNumber + this.getValue();
        }
    },
    STRAIGHT(30, "Straight", 5) {
        public int calculateCombination(int dieNumber) {
            int score = 0;
            for (int i = 0; i < this.diceCount; i++) {
                score += (dieNumber - i);
            }
            return score + this.combinationValue;
        }
    },
    FOUR_OF_A_KIND(40, "Four of a Kind", 4) {
        @Override
        public int calculateCombination(int dieNumber) {
            return (4 * dieNumber) + this.getValue();
        }
    },
    GENERALA(50, "Generala", DiceRolled.getNumberOfDice()) {
        @Override
        public int calculateCombination(int dieNumber) {
            return (DiceRolled.getNumberOfDice() * dieNumber) + this.getValue();
        }
    };

    int combinationValue;

    String label;

    int diceCount;

    CombinationEnum(int value, String label, int diceCount) {
        this.label = label;
        this.combinationValue = value;
        this.diceCount = diceCount;
    }

    public int getValue() {
        return this.combinationValue;
    }

    public String getLabel() {
        return this.label;
    }

    public int getDiceCount() {
        return this.diceCount;
    }
}
