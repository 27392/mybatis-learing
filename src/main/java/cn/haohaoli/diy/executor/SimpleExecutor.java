package cn.haohaoli.diy.executor;

import cn.haohaoli.diy.executor.statement.StatementHandler;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.MappedStatement;
import cn.haohaoli.diy.transaction.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.List;

/**
 * @author lwh
 */
@Slf4j
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Transaction transaction, Configuration configuration) {
        super(transaction, configuration);
    }

    @Override
    public <E> List<E> query(MappedStatement mapperStatement, Object parameter) throws Exception {
        Statement statement = null;
        try {
            StatementHandler statementHandler = configuration.newStatement(mapperStatement, parameter);
            Connection       connection       = super.getConnection();
            statement = statementHandler.prepare(connection);
            statementHandler.parameterize(statement);
            return statementHandler.query(statement);
        } finally {
            super.closeStatement(statement);
        }
    }

    @Override
    public int update(MappedStatement mapperStatement, Object parameter) throws Exception {
        Statement statement = null;
        try {
            StatementHandler statementHandler = configuration.newStatement(mapperStatement, parameter);

            Connection connection = super.getConnection();
            statement = statementHandler.prepare(connection);
            statementHandler.parameterize(statement);
            return statementHandler.update(statement);
        } finally {
            super.closeStatement(statement);
        }
    }

    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }
}
