package cn.haohaoli.diy.session;

import java.util.List;

/**
 * @author lwh
 */
public interface SqlSession extends AutoCloseable {

    <E> List<E> selectList(String statementId) ;

    <E> List<E> selectList(String statementId, Object parameter) ;

    <E> E selectOne(String statementId) ;

    <E> E selectOne(String statementId, Object parameter) ;

    int update(String statementId) ;

    int update(String statementId, Object parameter) ;

    int delete(String statementId) ;

    int delete(String statementId, Object parameter) ;

    int insert(String statementId, Object parameter) ;

    <T> T getMapper(Class<T> clazz);

    void commit();

    void rollback();
}
