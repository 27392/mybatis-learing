package cn.haohaoli.diy.session;

/**
 * @author lwh
 */
public interface SqlSessionFactory {

    SqlSession openSession();

    SqlSession openSession(boolean autoCommit);
}
