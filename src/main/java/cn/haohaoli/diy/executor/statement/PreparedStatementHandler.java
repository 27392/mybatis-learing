package cn.haohaoli.diy.executor.statement;

import cn.haohaoli.diy.mapping.BoundSql;
import cn.haohaoli.diy.session.MappedStatement;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author lwh
 */
@Slf4j
public class PreparedStatementHandler extends BaseStatementHandler {

    public PreparedStatementHandler(MappedStatement mappedStatement, Object parameter) {
        super(mappedStatement, parameter);
    }

    @Override
    public Statement prepare(Connection connection) throws SQLException {
        BoundSql boundSql = mappedStatement.getBoundSql();
        log.debug(boundSql.getSqlText());
        return connection.prepareStatement(boundSql.getSqlText());
    }

    @Override
    public void parameterize(Statement statement) throws SQLException {
        parameterHandler.setParameters((PreparedStatement) statement);
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException {
        ((PreparedStatement) statement).execute();
        return resultSetHandler.handleResultSets(statement);
    }

    @Override
    public int update(Statement statement) throws SQLException {
        return ((PreparedStatement) statement).executeUpdate();
    }
}
