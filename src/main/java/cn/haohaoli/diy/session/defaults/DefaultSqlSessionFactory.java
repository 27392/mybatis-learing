package cn.haohaoli.diy.session.defaults;

import cn.haohaoli.diy.executor.Executor;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.SqlSession;
import cn.haohaoli.diy.session.SqlSessionFactory;
import cn.haohaoli.diy.transaction.JdbcTransaction;
import cn.haohaoli.diy.transaction.Transaction;
import lombok.RequiredArgsConstructor;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    @Override
    public SqlSession openSession() {
        return this.openSession(true);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        Transaction transaction = new JdbcTransaction(configuration.getDataSource(), autoCommit);
        Executor executor = configuration.newExecutor(transaction, configuration.getDefaultExecutorType());
        return new DefaultSqlSession(configuration, executor);
    }

}
