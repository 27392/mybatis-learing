package cn.haohaoli.diy.executor.parameter;

import cn.haohaoli.diy.exceptions.BindingException;
import cn.haohaoli.diy.mapping.BoundSql;
import cn.haohaoli.diy.parsing.ParameterMapping;
import cn.haohaoli.diy.session.MappedStatement;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class DefaultParameterHandler implements ParameterHandler {

    private final MappedStatement mappedStatement;
    private final Object          parameter;

    @SuppressWarnings("rawtypes")
    @Override
    public void setParameters(PreparedStatement ps) throws SQLException {
        BoundSql               boundSql             = mappedStatement.getBoundSql();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();

        if (parameterMappingList == null || parameterMappingList.isEmpty()) {
            return;
        }

        try {
            Class<?> aClass = parameter.getClass();
            for (int i = 0; i < parameterMappingList.size(); i++) {
                String context = parameterMappingList.get(i).getContext();

                Object value;
                if (Map.class.isAssignableFrom(aClass)) {
                    value = ((Map) parameter).get(i);
                } else {
                    Field declaredField = aClass.getDeclaredField(context);
                    declaredField.setAccessible(true);
                    value = declaredField.get(parameter);
                }
                ps.setObject(i + 1, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new BindingException(e);
        }
    }
}
