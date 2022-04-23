package cn.haohaoli.diy.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author lwh
 */
@NoArgsConstructor
public class MybatisException extends RuntimeException {

    public MybatisException(Throwable cause) {
        super(cause);
    }

    public MybatisException(String message) {
        super(message);
    }

    public MybatisException(String message, Throwable cause) {
        super(message, cause);
    }
}
