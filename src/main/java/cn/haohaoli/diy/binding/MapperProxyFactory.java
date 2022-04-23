package cn.haohaoli.diy.binding;

import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author lwh
 */
public class MapperProxyFactory<T> {

    private final Configuration configuration;
    private final Class<T>      mapperInterface;

    public MapperProxyFactory(Configuration configuration, Class<T> type) {
        this.configuration = configuration;
        this.mapperInterface = type;
    }

    @SuppressWarnings("unchecked")
    public T newInstance(SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, new MapperProxy(configuration, sqlSession));
    }

}
