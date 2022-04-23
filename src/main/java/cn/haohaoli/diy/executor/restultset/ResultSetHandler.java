package cn.haohaoli.diy.executor.restultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author lwh
 */
public interface ResultSetHandler {

    <E> List<E> handleResultSets(Statement statement) throws SQLException;
}
