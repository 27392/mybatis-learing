package cn.haohaoli.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC方式查询
 *
 * @author lwh
 */
@Slf4j
public class JDBCWayQuery {

    public static void main(String[] args) throws Exception {

        original();

        template();
    }

    /**
     * 最原始的办法
     */
    public static void original() throws Exception {

        // 1. 定义sql语句
        String sql = "select * from user where name = ?";

        try (
                // 2. 通过驱动管理类获取数据库连接
                Connection connection = DriverManager.getConnection("jdbc:mysql:/mybatis?characterEncoding=utf-8", "root", "root");
                // 3. 获取预处理statement
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            // 4. 设置参数
            preparedStatement.setString(1, "tom");

            // 5. 向数据库发出sql执⾏查询,查询出结果集
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // 6. 遍历查询结果集
                List<User> list = new ArrayList<>();
                while (resultSet.next()) {
                    int    id       = resultSet.getInt("id");
                    String username = resultSet.getString("name");
                    User   user     = new User();
                    user.setId(id);
                    user.setName(username);
                    list.add(user);
                }
                log.info("{}", list);
            }
        }
    }

    /**
     * 模板封装
     *
     * @throws Exception
     */
    public static void template() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // 新增
        int insert = jdbcTemplate.update("insert into user(id, name) values(?, ?)", 100, "ymi");
        log.info("insert: {}", insert);

        // 查询
        List<User> query = jdbcTemplate.query("select * from user", resultSet -> {
            int    id       = resultSet.getInt("id");
            String username = resultSet.getString("name");
            User   user     = new User();
            user.setId(id);
            user.setName(username);
            return user;
        });
        log.info("list: {}", query);

        // 更新
        int update = jdbcTemplate.update("update user set name = ? where id = ?", "tim", 2);
        log.info("update: {}", update);

        // 删除
        int delete = jdbcTemplate.update("delete from user where id = ?", 100);
        log.info("delete: {}", delete);

        // 查询
        List<User> queryOne = jdbcTemplate.query("select * from user where id = ? and name = ?", resultSet -> {
            int    id       = resultSet.getInt("id");
            String username = resultSet.getString("name");
            User   user     = new User();
            user.setId(id);
            user.setName(username);
            return user;
        }, 1, "tom");
        log.info("queryOne: {}", queryOne);
    }
}
