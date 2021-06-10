package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.entity.User;
import cn.guosgbin.mybatis.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/6/8 23:09
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 *
 * 测试解析SQL
 */
public class buildSqlTest {

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
     * 测试动态标签
     */
    @Test
    public void testDynamic() {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = new User();
        user.setName("烧瓶");
        User user1 = userMapper.selectByCondition01(user);
        System.out.println(user1);
    }

    /**
     * 测试$符号
     */
    @Test
    public void testDollar() {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user1 = userMapper.selectByCondition02("烧瓶");
        System.out.println(user1);
    }

    /**
     *
     */
    @Test
    public void testNothing() {

    }
}
