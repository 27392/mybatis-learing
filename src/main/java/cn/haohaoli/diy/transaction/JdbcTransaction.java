package cn.haohaoli.diy.transaction;

import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class JdbcTransaction implements Transaction {

    private final DataSource dataSource;
    private final boolean    autoCommit;

    private Connection connection;

    @Override
    public void commit() throws SQLException {
        if (this.autoCommit) {
            return;
        }
        connection.commit();
    }

    @Override
    public void rollback() throws SQLException {
        if (this.autoCommit) {
            return;
        }
        connection.rollback();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.connection = dataSource.getConnection();
            this.connection.setAutoCommit(autoCommit);
        }
        return this.connection;
    }
}
