package cn.guosgbin.mybatis.example.mapper;

import cn.guosgbin.mybatis.example.entity.User;

import java.util.List;

public interface UserMapper {
    /**
     * 查询所有的用户
     */
    List<User> list();
}