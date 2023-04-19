package firesea.testserver.error;

public class DuplicateNickname extends RuntimeException {

    public DuplicateNickname() {
        super();
    }

    public DuplicateNickname(String message) {
        super(message);
    }

    public DuplicateNickname(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateNickname(Throwable cause) {
        super(cause);
    }

    protected DuplicateNickname(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
