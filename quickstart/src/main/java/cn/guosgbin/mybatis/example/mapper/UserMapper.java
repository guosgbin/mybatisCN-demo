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
     * 根据ID修改名字
     */
    int updateNameById(@Param("id") Integer id, @Param("name") String name);
}