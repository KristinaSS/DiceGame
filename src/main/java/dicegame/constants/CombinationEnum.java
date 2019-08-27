package dicegame.constants;

import dicegame.elements.DiceRolled;
import dicegame.interfaces.Calculable;

//todo javadoc
public enum CombinationEnum implements Calculable {
    PAIR(10, "Pair", 2) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return dieNumber * this.getDiceCount() + this.getValue();
        }
    },
    TRIPLE(20, "Triple", 3) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return this.getDiceCount() * dieNumber + this.getValue();
        }
    },
    DOUBLE_PAIR(15, "Double Pair", 2) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return dieNumber * this.getDiceCount() + this.getValue();
        }
    },
    FULL_HOUSE(25, "Full House", 1) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return dieNumber + this.getValue();
        }
    },
    STRAIGHT(30, "Straight", 5) {
        public int calculateCombination(final int dieNumber) {
            int score = 0;
            for (int i = 0; i < this.getDiceCount(); i++) {
                score += dieNumber - i;
            }
            return score + this.getValue();
        }
    },
    FOUR_OF_A_KIND(40, "Four of a Kind", 4) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return this.getDiceCount() * dieNumber + this.getValue();
        }
    },
    GENERALA(50, "Generala", DiceRolled.getNumberOfDice()) {
        @Override
        public int calculateCombination(final int dieNumber) {
            return DiceRolled.getNumberOfDice() * dieNumber + this.getValue();
        }
    };

    private int combinationValue;

    private String label;

    private int diceCount;

    CombinationEnum(final int value,
                    final String enumLabel,
                    final int numOfDice) {
        this.label = enumLabel;
        this.combinationValue = value;
        this.diceCount = numOfDice;
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
