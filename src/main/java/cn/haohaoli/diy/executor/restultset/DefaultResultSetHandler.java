package cn.haohaoli.diy.executor.restultset;

import cn.haohaoli.diy.exceptions.BindingException;
import cn.haohaoli.diy.session.MappedStatement;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;

    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(Statement statement) throws SQLException {

        try (ResultSet resultSet = statement.getResultSet()) {

            String   resultType      = mappedStatement.getResultType();
            Class<?> resultTypeClass = Class.forName(resultType);

            List<Object> objects = new ArrayList<>(resultSet.getRow());
            while (resultSet.next()) {

                Object            object   = this.createObject(resultTypeClass);
                ResultSetMetaData metaData = resultSet.getMetaData();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnName  = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(columnName);

                    Field declaredField = resultTypeClass.getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    declaredField.set(object, columnValue);
                }
                objects.add(object);
            }
            return (List<E>) objects;
        } catch (ReflectiveOperationException e) {
            throw new BindingException(e);
        }
    }

    /**
     * 创建对象
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private <T> T createObject(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
