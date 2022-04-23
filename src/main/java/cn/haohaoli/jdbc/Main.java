package cn.haohaoli.jdbc;

import cn.haohaoli.diy.io.Resources;
import cn.haohaoli.diy.session.SqlSession;
import cn.haohaoli.diy.session.SqlSessionFactory;
import cn.haohaoli.diy.session.SqlSessionFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

/**
 * @author lwh
 */
@Slf4j
public class Main {

    public static void main(String[] args) throws Exception {

        InputStream              resourceAsStream         = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory        sessionFactory           = sqlSessionFactoryBuilder.build(resourceAsStream);

        try (SqlSession sqlSession = sessionFactory.openSession(true)) {

            // 传统方式
            Object o = sqlSession.selectOne("cn.haohaoli.jdbc.UserMapper.selectById", User.builder().id(1).build());
            log.info("{}", o);

            List<Object> objects = sqlSession.selectList("cn.haohaoli.jdbc.UserMapper.selectAll");
            log.info("{}", objects);

            // 动态代理方式
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            List<User> users = mapper.selectAll();
            log.info("selectAll: {}", users);

            User user1 = mapper.selectById(User.builder().id(1).build());
            log.info("selectById: {}", user1);

            User maxi2 = mapper.selectByIdAndName(1, "maxi");
            log.info("selectByIdAndName: {}", maxi2);

            int maxi = mapper.updateById(User.builder().id(1).name("maxi").build());
            log.info("update: {}", maxi);

            User user2 = mapper.selectById(User.builder().id(1).build());
            log.info("selectById: {}", user2);

            int maxi1 = mapper.insert(User.builder().id(99).name("oke").build());
            log.info("insert: {}", maxi1);

            int maxi12 = mapper.deleteById(User.builder().id(99).name("oke").build());
            log.info("deleteById: {}",maxi12);

            List<User> user1s = mapper.selectAll();
            log.info("selectAll: {}", user1s);

            sqlSession.rollback();
        }
    }
}
