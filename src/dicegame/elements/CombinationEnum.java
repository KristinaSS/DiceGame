package dicegame.elements;


public enum CombinationEnum implements Calculable {
    PAIR( 10, "Pair"){
        @Override
        public int calculateCombination(int dieNumber) {
            return  (dieNumber * 2) + this.getValue();
        }
    },
    TRIPLE( 20, "Triple"){
        @Override
        public int calculateCombination(int dieNumber) {
/*            if(dieNumber == 0)
                return;*/
            return  (3 * dieNumber) + this.getValue();
        }
    },
    DOUBLE_PAIR( 15, "Double Pair"){
        @Override
        public int calculateCombination(int dieNumber) {
            return (dieNumber* 2) + this.getValue();
        }
    },
    FULL_HOUSE( 25, "Full House"){
        @Override
        public int calculateCombination(int dieNumber) {
            return dieNumber + this.getValue();
        }
    },
    STRAIGHT( 30, "Straight"){
        public int calculateCombination(int dieNumber) {
            int score = 0;
            for (int i = 0; i < 5; i++) {
                score += (dieNumber - i);
            }
            return score + this.combinationValue;
        }
    },
    FOUR_OF_A_KIND( 40, "Four of a Kind"){
        @Override
        public int calculateCombination(int dieNumber) {
            return  (4 * dieNumber) + this.getValue();
        }
    },
    GENERALA( 50, "Generala"){
        @Override
        public int calculateCombination(int dieNumber) {
             return (5 * dieNumber) + this.getValue();
        }
    };

    int combinationValue;
    String label;

    CombinationEnum(int value, String label) {
        this.label = label;
        combinationValue = value;
    }

    public int getValue() {
        return combinationValue;
    }

    public String getLabel() throws NullPointerException {
        return label;
    }
}
