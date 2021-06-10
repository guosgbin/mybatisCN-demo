package cn.guosgbin.mybatis.example.mapper;

import cn.guosgbin.mybatis.example.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有的用户
     */
    List<User> list();

    /**
     * 根据ID查询
     */
    User selectById(Integer id);

    /**
     * 通过制定状态查询
     *
     * @param user
     * @return
     */
    User selectByCondition01(@Param("user") User user);

    /**
     * 测试 ${}
     * @param name
     * @return
     */
    User selectByCondition02(@Param("name") String name);

    /**
     * 根据ID修改名字
     */
    int updateNameById(@Param("id") Integer id, @Param("name") String name);
}