package dicegame.elements;

import java.util.List;

public interface Calculable {
    int calculateCombination(List<Integer>dieNumbers);
    void setDieNumber(int i);
    int getDieNumber();
}
