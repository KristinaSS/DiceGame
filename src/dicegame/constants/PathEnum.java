package dicegame.constants;

public enum PathEnum
{
	KRISTINA_WORK_PATH("C:/Users/kristina.stoyanova/IdeaProjects/DiceGame/rescources/config.properties"),
	YORDAN_WORK_PATH("C:\\Users\\Yordan\\Desktop\\DiceGame-master\\DiceGame-master\\rescources\\config.properties");

	String filePathStr;

	PathEnum(String filePathStr)
	{
		this.filePathStr = filePathStr;
	}

	public String getFilePathStr()
	{
		return this.filePathStr;
	}
}
