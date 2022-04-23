package cn.haohaoli.diy.executor.statement;

import cn.haohaoli.diy.executor.parameter.ParameterHandler;
import cn.haohaoli.diy.executor.restultset.ResultSetHandler;
import cn.haohaoli.diy.mapping.BoundSql;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.MappedStatement;

/**
 * @author lwh
 */
public abstract class BaseStatementHandler implements StatementHandler {

    protected final Configuration   configuration;
    protected final MappedStatement mappedStatement;
    protected final Object          parameter;
    protected final BoundSql        boundSql;

    protected final ParameterHandler parameterHandler;
    protected final ResultSetHandler resultSetHandler;

    protected BaseStatementHandler(MappedStatement mappedStatement, Object parameter) {
        this.configuration = mappedStatement.getConfiguration();
        this.mappedStatement = mappedStatement;
        this.boundSql = mappedStatement.getBoundSql();
        this.parameter = parameter;

        this.parameterHandler = this.configuration.newParameterHandler(mappedStatement, parameter);
        this.resultSetHandler = this.configuration.newResultSetHandler(mappedStatement);
    }
}
