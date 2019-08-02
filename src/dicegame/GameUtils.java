package dicegame;

import dicegame.application.Game;
import dicegame.elements.CombinationEnum;
import dicegame.elements.Dice;
import dicegame.elements.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class GameUtils {
    private static GameUtils gameUtilsInstance = null;

    public static GameUtils getInstance() {
        if (gameUtilsInstance == null)
            gameUtilsInstance = new GameUtils();
        return gameUtilsInstance;
    }

    private GameUtils() {
    }

    //Utils

    public Map.Entry<CombinationEnum, Integer> getEntry(int i) {
        int j = 0;
        for (Map.Entry<CombinationEnum, Integer> entry : Dice.getSortScore().entrySet()) {
            if (j++ == i)
                return entry;
        }
        return null;
    }

    public HashMap<CombinationEnum, Integer> sortByValue() {

        List<Map.Entry<CombinationEnum, Integer>> list =
                new LinkedList<>(Dice.getSortScore().entrySet());

        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        HashMap<CombinationEnum, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<CombinationEnum, Integer> aa : list)
            temp.put(aa.getKey(), aa.getValue());
        return temp;
    }

    public void endGame(List<Player> playerList, Player winner) {
        int previousScore = -1;
        System.out.println(">>>  RESULTS  <<<<");
        System.out.println("Place       Player       Score");
        int i = 1;
        playerList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));

        if (!(winner == null)) {
            System.out.println("GENERALA! Player " + winner.getPlayerNumber());
            playerList.remove(winner);
            System.out.println(i + ".         Player " + winner.getPlayerNumber() + "   ->  " + winner.getScore());
        }

        for (Player p : playerList) {
            if (previousScore > p.getScore() || previousScore == -1)
                i++;
            System.out.println(i + ".         Player " + p.getPlayerNumber() + "   ->  " + p.getScore());
            previousScore = p.getScore();
        }
        System.out.println("----------------------------------------------------------------------------");
    }

    public void printRound(Player p,
                           int round,
                           int oldScore,
                           int rolledScore,
                           String typeOfCombination) {
        Dice.getInstance().sortDiceNaturalOrder();

        String rolledDice =Dice.getDiceRolled().toString();
        rolledDice =rolledDice.replace('[',' ');
        rolledDice =rolledDice.replace(']',' ');


        System.out.println(">>> round: " + round);
        System.out.println(">player " + p.getPlayerNumber() + ":");
        System.out.println("current score: " + oldScore);
        System.out.println("dice roll:" + rolledDice + "-> " + typeOfCombination + " (" + rolledScore + ") ");
        System.out.println("new score: " + p.getScore());
        System.out.println();
    }

    public List<Player> fillPlayerList(int playerNum) {
        List<Player> playerList = new ArrayList<>();
        while (playerNum-- > 0) {
            playerList.add(new Player(playerList.size() + 1));
        }
        return playerList;
    }

    protected static CombinationEnum getEnumWithIndex(int i) throws NullPointerException {
        for (CombinationEnum e : CombinationEnum.values()) {
            if (e.getIndex() == i)
                return e;
        }
        throw new NullPointerException("No Combination Enum with index " + i + " exist");
    }

    public void readPropertiesFile(Path path){
        try(InputStream input = Files.newInputStream(path)){
            Properties prop = new Properties();
            prop.load(input);

            Game.getInstance().setRounds(Integer.parseInt(prop.getProperty("roundCount")));
            Game.getInstance().setPlayerCount(Integer.parseInt(prop.getProperty("playerCount")));

            Dice.getInstance().setNumberOfDices(Integer.parseInt(prop.getProperty("diceCount")));
            Dice.getInstance().setNumberOfSides(Integer.parseInt(prop.getProperty("numberOfSidesOnDice")));

        } catch (IOException exception){
            exception.printStackTrace();
            System.exit(-1);
        }
    }
}

