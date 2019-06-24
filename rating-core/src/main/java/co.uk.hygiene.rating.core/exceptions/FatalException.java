package co.uk.hygiene.rating.core.exceptions;
/**
 * @author Kannan Gnanasigamani
 *
 */
public class FatalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalException(Throwable cause) {
        super(cause);
    }

    public FatalException(String message) {
        super(message);
    }
}