package cn.haohaoli.diy.session;

import cn.haohaoli.diy.binding.MapperRegistry;
import cn.haohaoli.diy.executor.Executor;
import cn.haohaoli.diy.executor.SimpleExecutor;
import cn.haohaoli.diy.executor.restultset.*;
import cn.haohaoli.diy.executor.parameter.DefaultParameterHandler;
import cn.haohaoli.diy.executor.parameter.ParameterHandler;
import cn.haohaoli.diy.executor.statement.PreparedStatementHandler;
import cn.haohaoli.diy.executor.statement.StatementHandler;
import cn.haohaoli.diy.transaction.Transaction;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwh
 */
@Getter
@Setter
@ToString
public class Configuration {

    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;

    private DataSource dataSource;

    private Map<String, MappedStatement> mapperStatementMap = new HashMap<>();

    private MapperRegistry mapperRegistry = new MapperRegistry(this);


    public void addMappedStatement(MappedStatement ms) {
        mapperStatementMap.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String statementId) {
        return mapperStatementMap.get(statementId);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 四大对象
    ///////////////////////////////////////////////////////////////////////////

    public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
        Executor executor;
        switch (executorType) {
            case SIMPLE:
                executor = new SimpleExecutor(transaction, this);
                break;
            default:
                executor = new SimpleExecutor(transaction, this);
        }
        return executor;
    }

    public StatementHandler newStatement(MappedStatement mapperStatement, Object parameter) {
        switch (mapperStatement.getStatementType()) {
            case PREPARED:
                return new PreparedStatementHandler(mapperStatement, parameter);
            default:
                throw new RuntimeException("");
        }
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameter) {
        return new DefaultParameterHandler(mappedStatement, parameter);
    }

    public ResultSetHandler newResultSetHandler(MappedStatement mappedStatement) {
        return new DefaultResultSetHandler(mappedStatement);
    }
}
