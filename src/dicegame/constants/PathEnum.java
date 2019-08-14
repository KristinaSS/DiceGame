package dicegame.constants;


public enum PathEnum {
    KRISTINA_WORK_PATH("C:/Users/kristina.stoyanova/IdeaProjects/DiceGame/src/dicegame/rescources/config.properties"),
    YORDAN_WORK_PATH("C:/Users/Yordan/Desktop/DiceGame-master/DiceGame-master/rescources/config.properties");
    //todo Dancho, your path wont work i refactored the resource package
    //to be honest i think this enum is redundant
    String filePathStr;

    PathEnum(String filePathStr) {
        this.filePathStr = filePathStr;
    }

    public String getFilePathStr() {
        return this.filePathStr;
    }
}
