package cn.haohaoli.diy.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author lwh
 */
@NoArgsConstructor
public class PersistenceException extends MybatisException {

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
