package dicegame.elements;


public enum CombinationEnum implements Calculable {
    PAIR(4, 10, "Pair"){
        @Override
        public int calculateCombination(int dieNumber) {
            return  (dieNumber * 2) + this.getValue();
        }
    },
    TRIPLE(5, 20, "Triple"){
        @Override
        public int calculateCombination(int dieNumber) {
/*            if(dieNumber == 0)
                return;*/
            return  (3 * dieNumber) + this.getValue();
        }
    },
    DOUBLE_PAIR(3, 15, "Double Pair"){
        @Override
        public int calculateCombination(int dieNumber) {
            return (dieNumber* 2) + this.getValue();
        }
    },
    FULL_HOUSE(6, 25, "Full House"){
/*        int tripleDie;
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
            Dice.getDiceRolled().removeIf(it -> it == tripleDie);
            this.pairDie = Game.getInstance().setPairDie();
            if(tripleDie==0 || pairDie==0) {
                tripleDie = 0;
                pairDie = 0;
            }
        }
        @Override
        public int getDieNumber() {
            return pairDie;
        }*/
        @Override
        public int calculateCombination(int dieNumber) {
/*            if(pairDie == 0 || tripleDie ==0)
                return;
            score = (2 * pairDie) + (3 * tripleDie) + this.getValue();*/
return 0;
        }
    }, //todo need to be fixed
    STRAIGHT(1, 30, "Straight"){
        public int calculateCombination(int dieNumber) {
            int score = 0;
            for (int i = 0; i < 5; i++) {
                score += (dieNumber - i);
            }
            return score + this.combinationValue;
        }
    },
    FOUR_OF_A_KIND(2, 40, "Four of a Kind"){
        @Override
        public int calculateCombination(int dieNumber) {
            return  (4 * dieNumber) + this.getValue();
        }
    },
    GENERALA(0, 50, "Generala"){
        @Override
        public int calculateCombination(int dieNumber) {
             return (5 * dieNumber) + this.getValue();
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
