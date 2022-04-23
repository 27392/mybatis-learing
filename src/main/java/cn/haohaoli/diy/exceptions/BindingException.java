package cn.haohaoli.diy.exceptions;

import lombok.NoArgsConstructor;

/**
 * @author lwh
 */
@NoArgsConstructor
public class BindingException  extends PersistenceException{

    public BindingException(Throwable cause) {
        super(cause);
    }

    public BindingException(String message) {
        super(message);
    }

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }
}
