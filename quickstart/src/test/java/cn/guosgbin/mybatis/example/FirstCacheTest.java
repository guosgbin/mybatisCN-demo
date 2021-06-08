package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.entity.User;
import cn.guosgbin.mybatis.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/5/31 0:15
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 *
 * 一级缓存测试
 */
public class FirstCacheTest {

    private SqlSession session;
    private SqlSessionFactory factory;

    @Before
    public void init() throws IOException {
        //1. 读取核心配置文件SqlMapConfig.xml
        InputStream is = Resources.getResourceAsStream("mybatis.xml");
        //2. 创建SqlSessionFactoryBuilder构造者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3. 使用构造者builder，根据配置文件的信息is，构造一个SqlSessionFactory工厂对象
        factory = builder.build(is);
        //4. 使用工厂对象factory，生产一个SqlSession对象
        session = factory.openSession();
    }

    /**
     * 1.sql 和参数必须相同
     * 2. 必须是相同的statementId
     * 3. 必须是同一个SQLSession  会话级缓存
     * 4. RowBounds 返回行数范围必须相同
     */
    @Test
    public void test1() {
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectById(1);

//        Object o = session.selectOne("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById", 1);
        RowBounds rowBounds = new RowBounds(0, 10);
        Object o = session.selectList("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById", 1, rowBounds);
        System.out.println(user == o);
    }

    /**
     * 一级命中的情况
     * 1.没有手动清空
     * 2.没有调用flushCache = true的查询
     * 3.没有调用update方法   （只要调了方法 全部清空一级缓存）
     * 4.没有关闭一级缓存  关闭一级缓存方法STATEMENT  但是并不是全部关闭一级缓存啊，一级缓存还会存在于 嵌套查询中（子查询）
     */
    @Test
    public void test2() {
        UserMapper mapper = session.getMapper(UserMapper.class);
        User user = mapper.selectById(1);

//        session.clearCache();

        User user2 = mapper.selectById(1);
        System.out.println(user == user2);

    }
}
