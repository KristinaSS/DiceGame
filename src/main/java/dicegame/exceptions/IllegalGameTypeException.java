package dicegame.exceptions;

public class IllegalGameTypeException extends RuntimeException{
    private IllegalGameTypeException(){
        super();
    }
    public IllegalGameTypeException(String message){
        super(message);
    }
}
