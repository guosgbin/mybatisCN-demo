package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/6/14 17:26
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 */
public class InParamTest {
    private SqlSession session;
    private SqlSessionFactory factory;
    private Configuration configuration;

    @Before
    public void init() throws IOException {
        //1. 读取核心配置文件SqlMapConfig.xml
        InputStream is = Resources.getResourceAsStream("mybatis.xml");
        //2. 创建SqlSessionFactoryBuilder构造者对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //3. 使用构造者builder，根据配置文件的信息is，构造一个SqlSessionFactory工厂对象
        factory = builder.build(is);

        configuration = factory.getConfiguration();
        //4. 使用工厂对象factory，生产一个SqlSession对象
        session = factory.openSession();
    }

    @Test
    public void paramTest01() throws NoSuchMethodException {
        Method method1 = TestInterface.class.getMethod("testMethod01", Integer.class);
        Method method2 = TestInterface.class.getMethod("testMethod02", Integer.class);

        ParamNameResolver resolver1 = new ParamNameResolver(configuration, method1);
        ParamNameResolver resolver2 = new ParamNameResolver(configuration, method2);

        String[] names1 = resolver1.getNames();
        String[] names2 = resolver2.getNames();
        System.out.println(Arrays.toString(names1));
        System.out.println(Arrays.toString(names2));

        System.out.println("======");

        Object namedParams1 = resolver1.getNamedParams(new Object[]{555});
        System.out.println(namedParams1);
        System.out.println(namedParams1.getClass());

        Object namedParams2 = resolver1.getNamedParams(new Object[]{666});
        System.out.println(namedParams2);
        System.out.println(namedParams2.getClass());
    }

    @Test
    public void paramTest02() throws NoSuchMethodException {
        Method method1 = TestInterface.class.getMethod("testMethod03", String.class, Integer.class);
        Method method2 = TestInterface.class.getMethod("testMethod04", String.class, Integer.class);

        ParamNameResolver resolver1 = new ParamNameResolver(configuration, method1);
        ParamNameResolver resolver2 = new ParamNameResolver(configuration, method2);

        String[] names1 = resolver1.getNames();
        String[] names2 = resolver2.getNames();
        System.out.println(Arrays.toString(names1));
        System.out.println(Arrays.toString(names2));

        System.out.println("======");

        Object namedParams1 = resolver1.getNamedParams(new Object[]{"大烧瓶", 555});
        System.out.println(namedParams1);
        System.out.println(namedParams1.getClass());

        Object namedParams2 = resolver1.getNamedParams(new Object[]{"热水壶", 666});
        System.out.println(namedParams2);
        System.out.println(namedParams2.getClass());
    }




    @Test
    public void paramTest03() throws NoSuchMethodException {
//        Method method1 = TestInterface.class.getMethod("testMethod01", Integer.class);
//        Method method1 = TestInterface.class.getMethod("testMethod02", Integer.class);
//        Method method1 = TestInterface.class.getMethod("testMethod03", String.class, Integer.class);
//        Method method1 = TestInterface.class.getMethod("testMethod04", String.class, Integer.class);
//        Method method1 = TestInterface.class.getMethod("testMethod05", Map.class);
//        Method method1 = TestInterface.class.getMethod("testMethod06", User.class);
        Method method1 = TestInterface.class.getMethod("testMethod07", User.class);


        ParamNameResolver resolver1 = new ParamNameResolver(configuration, method1);

        String[] names1 = resolver1.getNames();
        System.out.println(Arrays.toString(names1));

//        Object namedParams1 = resolver1.getNamedParams(new Object[]{333});
//        Object namedParams1 = resolver1.getNamedParams(new Object[]{555});
//        Object namedParams1 = resolver1.getNamedParams(new Object[]{"大烧瓶", 333});
//        Object namedParams1 = resolver1.getNamedParams(new Object[]{"大烧瓶", 666});

//        HashMap<Object, Object> map = new HashMap<>();
//        map.put(1,"111");
//        map.put(2,"222");
//        Object namedParams1 = resolver1.getNamedParams(new Object[]{map});


        User user = new User();
        user.setName("孔洁");
        user.setAge(23);

        Object namedParams1 = resolver1.getNamedParams(new Object[]{user});
//        Object namedParams1 = resolver1.getNamedParams(new Object[]{user});



        System.out.println(namedParams1);
        System.out.println(namedParams1.getClass());

    }









    /** =============== 测试方法开始 =============== */
    public interface TestInterface {
        void testMethod01(Integer age);
        void testMethod02(@Param("bigAge") Integer age);
        void testMethod03(String name, Integer age);
        void testMethod04(String name,  @Param("bigAge") Integer age);
        void testMethod05(Map<Integer, String> map);
        void testMethod06(User user);
        void testMethod07(@Param("userrrr") User user);

    }

    /** =============== 测试方法结束 =============== */



}
