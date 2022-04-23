package cn.haohaoli.diy.executor;

import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.transaction.Transaction;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public abstract class BaseExecutor implements Executor {

    protected final Transaction   transaction;
    protected final Configuration configuration;

    /**
     * 获取数据库连接
     *
     * @return
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        return transaction.getConnection();
    }

    /**
     * 关闭statement
     *
     * @param statement
     */
    protected void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // ignore
            }
        }
    }
}
