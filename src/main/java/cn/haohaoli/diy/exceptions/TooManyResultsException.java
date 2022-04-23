package cn.haohaoli.diy.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author lwh
 */
@NoArgsConstructor
public class TooManyResultsException extends PersistenceException {

    public TooManyResultsException(Throwable cause) {
        super(cause);
    }

    public TooManyResultsException(String message) {
        super(message);
    }

    public TooManyResultsException(String message, Throwable cause) {
        super(message, cause);
    }
}
