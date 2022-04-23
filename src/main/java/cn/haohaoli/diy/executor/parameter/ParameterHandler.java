package cn.haohaoli.diy.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lwh
 */
public interface ParameterHandler {

    void setParameters(PreparedStatement ps) throws SQLException;
}
