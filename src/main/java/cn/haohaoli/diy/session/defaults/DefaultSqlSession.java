package cn.haohaoli.diy.session.defaults;

import cn.haohaoli.diy.exceptions.PersistenceException;
import cn.haohaoli.diy.exceptions.TooManyResultsException;
import cn.haohaoli.diy.executor.Executor;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.MappedStatement;
import cn.haohaoli.diy.session.SqlSession;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;
    private final Executor      executor;

    @Override
    public <E> List<E> selectList(String statementId) {
        return this.selectList(statementId, null);
    }

    @Override
    public <E> List<E> selectList(String statementId, Object parameter) {
        MappedStatement mapperStatement = configuration.getMappedStatement(statementId);
        try {
            return executor.query(mapperStatement, parameter);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public <E> E selectOne(String statementId) {
        return this.selectOne(statementId, null);
    }

    @Override
    public <E> E selectOne(String statementId, Object parameter) {
        List<E> list = this.selectList(statementId, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException();
        } else {
            return null;
        }
    }

    @Override
    public int update(String statementId) {
        return this.update(statementId, null);
    }

    @Override
    public int update(String statementId, Object parameter) {
        MappedStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        try {
            return executor.update(mapperStatement, parameter);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public int delete(String statementId) {
        return this.update(statementId, null);
    }

    @Override
    public int delete(String statementId, Object parameter) {
        return this.update(statementId, parameter);
    }

    @Override
    public int insert(String statementId, Object parameter) {
        return this.update(statementId, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        return this.configuration.getMapper(clazz, this);
    }

    @Override
    public void commit() {
        try {
            executor.getTransaction().commit();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            executor.getTransaction().rollback();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void close() throws Exception {
        executor.getTransaction().close();
    }
}
