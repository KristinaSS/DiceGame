package dicegame.exceptions;

public class IllegalGameTypeException extends RuntimeException{
    public IllegalGameTypeException(String message){
        super(message);
    }
}
