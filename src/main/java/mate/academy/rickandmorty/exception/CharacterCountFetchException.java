package mate.academy.rickandmorty.exception;

public class CharacterCountFetchException extends RuntimeException {
    public CharacterCountFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
