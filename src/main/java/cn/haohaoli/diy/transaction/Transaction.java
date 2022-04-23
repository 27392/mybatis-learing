package cn.haohaoli.diy.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lwh
 */
public interface Transaction {

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    Connection getConnection() throws SQLException;

}
