package cn.haohaoli.diy.binding;

import cn.haohaoli.diy.exceptions.BindingException;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.MappedStatement;
import cn.haohaoli.diy.session.SqlCommandType;
import cn.haohaoli.diy.session.SqlSession;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class MapperProxy implements InvocationHandler {

    private final Configuration configuration;
    private final SqlSession    sqlSession;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?>        returnType      = method.getReturnType();
        String          statementId     = method.getDeclaringClass().getName() + "." + method.getName();
        MappedStatement mapperStatement = configuration.getMapperStatementMap().get(statementId);
        Object          parameter       = this.convertParameter(method, args);

        SqlCommandType sqlCommandType = mapperStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case SELECT:
                if (Collection.class.isAssignableFrom(returnType)) {
                    return sqlSession.selectList(statementId, parameter);
                }
                return sqlSession.selectOne(statementId, parameter);
            case DELETE:
                return sqlSession.delete(statementId, parameter);
            case INSERT:
                return sqlSession.insert(statementId, parameter);
            case UPDATE:
                return sqlSession.update(statementId, parameter);
            default:
                throw new BindingException("错误的类型: " + sqlCommandType);
        }
    }

    /**
     * 参数装换
     *
     * @param method
     * @param args
     * @return
     */
    private Object convertParameter(Method method, Object[] args) {
        Object parameter;
        if (args == null || args.length == 0) {
            parameter = null;
        } else if (args.length == 1) {
            parameter = args[0];
        } else {
            // 存在多个则转换成map对象
            Parameter[] parameters = method.getParameters();
            parameter = IntStream.rangeClosed(0, parameters.length - 1).boxed().collect(Collectors.toMap(r -> r, r -> args[r]));
        }
        return parameter;
    }
}
