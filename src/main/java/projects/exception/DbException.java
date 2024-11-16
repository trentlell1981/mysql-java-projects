package projects.exception;

/*
 * This class allows for cleaner, more readable code by enabling exceptions to be thrown without needing 
 * to declare them in method signatures. It also provides flexibility by allowing custom error messages and
 *  retaining the original cause of the exception, making it easier to diagnose issues in database
 *   interactions while keeping the code base streamlined and consistent.
 */

@SuppressWarnings("serial")
public class DbException extends RuntimeException {

    public DbException(String message) {
        super(message);
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
