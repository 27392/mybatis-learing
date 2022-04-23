package cn.haohaoli.jdbc;

import java.util.List;

/**
 * @author lwh
 */
public interface UserMapper {

    List<User> selectAll();

    User selectById(User user);

    User selectByIdAndName(Integer id,String name);

    int insert(User user);

    int updateById(User user);

    int deleteById(User user);

}
