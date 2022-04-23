package cn.haohaoli.diy.io;

import lombok.experimental.UtilityClass;

import java.io.InputStream;

/**
 * @author lwh
 */
@UtilityClass
public class Resources {

    public InputStream getResourceAsStream (String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }

}
