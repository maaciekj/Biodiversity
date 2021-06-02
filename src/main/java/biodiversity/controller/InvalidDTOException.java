package biodiversity.controller;

public class InvalidDTOException extends RuntimeException {

    public InvalidDTOException(String message) {
        super(message);
    }
}
