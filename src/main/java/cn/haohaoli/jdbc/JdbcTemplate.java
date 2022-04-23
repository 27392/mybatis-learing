package cn.haohaoli.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lwh
 */
public class JdbcTemplate {

    /**
     * 查询
     *
     * @param sql
     * @param rowMapper
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public final <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(connection, sql);
             ResultSet resultSet = getResultSet(preparedStatement, args);) {
            return rowMapping(resultSet, rowMapper);
        }
    }

    /**
     * 更新
     *
     * @param sql
     * @param args
     * @return
     * @throws Exception
     */
    public final int update(String sql, Object... args) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = getPreparedStatement(connection, sql)) {
            preparedArgs(preparedStatement, args);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql:/mybatis?characterEncoding=utf-8", "root", "root");
    }

    /**
     * 获取预编译statement
     *
     * @param connection
     * @param sql
     * @return
     * @throws SQLException
     */
    protected PreparedStatement getPreparedStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * 获取结果
     *
     * @param preparedStatement
     * @param args
     * @return
     * @throws SQLException
     */
    protected ResultSet getResultSet(PreparedStatement preparedStatement, Object... args) throws SQLException {
        this.preparedArgs(preparedStatement, args);
        return preparedStatement.executeQuery();
    }

    /**
     * 处理参数
     *
     * @param preparedStatement
     * @param args
     * @throws SQLException
     */
    protected void preparedArgs(PreparedStatement preparedStatement, Object... args) throws SQLException {
        if (args == null) {
            return;
        }
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
    }

    /**
     * 行映射
     *
     * @param resultSet
     * @param rowMapper
     * @param <T>
     * @return
     * @throws Exception
     */
    protected <T> List<T> rowMapping(ResultSet resultSet, RowMapper<T> rowMapper) throws Exception {
        int     row  = resultSet.getRow();
        List<T> list = new ArrayList<>(row);

        while (resultSet.next()) {
            T t = rowMapper.mapRow(resultSet);
            list.add(t);
        }
        return list;
    }

    /**
     * 行映射
     *
     * @param <T>
     */
    public interface RowMapper<T> {

        /**
         * 映射
         *
         * @param resultSet
         * @return
         * @throws Exception
         */
        T mapRow(ResultSet resultSet) throws Exception;
    }
}
