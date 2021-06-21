package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.entity.User;
import cn.guosgbin.mybatis.example.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class quickstartTest {
    @Test
    public void testQuickStart() throws IOException {
        //1. 读取核心配置文件SqlMapConfig.xml
        InputStream is = Resources.getResourceAsStream("mybatis.xml");
        //2. 创建SqlSessionFactoryBuilder构造者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3. 使用构造者builder，根据配置文件的信息is，构造一个SqlSessionFactory工厂对象
        SqlSessionFactory factory = builder.build(is);
        // 4. 使用工厂对象factory，生产一个SqlSession对象
        SqlSession session = factory.openSession();
//        SqlSession session = factory.openSession(ExecutorType.BATCH);
        // 5. 使用SqlSession对象，获取映射器UserDao接口的代理对象
        UserMapper dao = session.getMapper(UserMapper.class);
        // 6. 调用UserDao代理对象的方法，查询所有用户

//       dao.updateNameById(1,"11");

//        User user = new User();
//        user.setName("111");
//        user.setAge(12);
//        dao.selectByCondition01(user);

        dao.selectUserByIds(Arrays.asList(1,2));

//        System.out.println(user);
//        List<User> users = dao.list();
//        for (User user : users) {
//            System.out.println(user);
//        }
        //7. 释放资源
        session.close();
        is.close();
    }
}
