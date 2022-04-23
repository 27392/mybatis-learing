package cn.haohaoli.diy.binding;

import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.SqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lwh
 */
public class MapperRegistry {

    private final Configuration configuration;

    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> void addMapper(Class<T> type) {
        knownMappers.put(type, new MapperProxyFactory<>(configuration, type));
    }

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<?> mapperProxyFactory = knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("");
        }
        return (T) mapperProxyFactory.newInstance(sqlSession);
    }
}
