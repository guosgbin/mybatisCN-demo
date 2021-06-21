package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.mapper.AccountMapper;
import cn.guosgbin.mybatis.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/6/15 8:38
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 */
public class SecondCacheTest {
    private SqlSession session;
    private SqlSession session2;
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
        session2 = factory.openSession();
    }

    @Test
    public void test01() {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        AccountMapper accountMapper = session.getMapper(AccountMapper.class);

        userMapper.list();
        accountMapper.list();

        System.out.println(session);

    }

    /**
     * 测试update清空二级缓存
     */
    @Test
    public void test02() {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.list();

        userMapper.list();

        System.out.println(session);

    }
}
