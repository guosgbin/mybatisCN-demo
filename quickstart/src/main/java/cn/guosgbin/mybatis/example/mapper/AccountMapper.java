package cn.guosgbin.mybatis.example.mapper;

import cn.guosgbin.mybatis.example.entity.User;

import java.util.List;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/6/15 8:34
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 */
public interface AccountMapper {
    /**
     * 查询所有的用户
     */
    List<User> list();
}
