package dicegame.elements;

import java.util.List;

public interface Calculable {
    void calculateCombination();
    void setDieNumber(int i);
    int getDieNumber();
    int getScore();
    void setScore(int i);
}
