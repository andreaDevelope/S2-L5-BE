package epicode.it.exception;

public class DuplicatedIsbnException extends RuntimeException{
    public DuplicatedIsbnException(String message) {
        super(message);
    }
}
