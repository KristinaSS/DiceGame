package dicegame.exceptions;

public class IllegalGameTypeException extends RuntimeException {
    public IllegalGameTypeException(final String message) {
        super(message);
    }
}
