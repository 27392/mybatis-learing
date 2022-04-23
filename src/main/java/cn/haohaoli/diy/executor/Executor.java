package cn.haohaoli.diy.executor;

import cn.haohaoli.diy.session.MappedStatement;
import cn.haohaoli.diy.transaction.Transaction;

import java.util.List;

/**
 * @author lwh
 */
public interface Executor {

    <E> List<E> query(MappedStatement mapperStatement, Object parameter) throws Exception;

    int update(MappedStatement mapperStatement, Object parameter) throws Exception;

    Transaction getTransaction();
}
