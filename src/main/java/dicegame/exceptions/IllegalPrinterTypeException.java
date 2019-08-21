package dicegame.exceptions;

public class IllegalPrinterTypeException extends RuntimeException {
    private IllegalPrinterTypeException(){
        super();
    }
    public IllegalPrinterTypeException(String message){
        super(message);
    }
}
