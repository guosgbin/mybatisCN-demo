package cn.guosgbin.mybatis.example;

import org.apache.ibatis.executor.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/5/30 22:55
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 *
 * 基础测试
 */
public class BaseExecutorTest {
  private static final String URL = "jdbc:mysql://8.129.224.250/mybatis_example";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "root@123";

  private Configuration configuration;
  private Connection connection;
  private JdbcTransaction jdbcTransaction;

  @Before
  public void init() throws Exception {
    InputStream is = Resources.getResourceAsStream("mybatis.xml");
    SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory factory = factoryBuilder.build(is);
    configuration = factory.getConfiguration();
    connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    jdbcTransaction = new JdbcTransaction(connection);
  }

  /**
   * 简单执行器测试
   */
  @Test
  public void simpleTest() throws SQLException {
    // 创建一个简单执行器
    SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
    // 获得映射的语句对象
    MappedStatement ms = configuration.getMappedStatement("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById");
    // 第一次执行查询
    List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT,
        SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));

    // 这里重复执行相同的SQL， 还是去执行两次预处理  （从打印日志可以看出）
    List<Object> list2 = executor.doQuery(ms, 1, RowBounds.DEFAULT,
            SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
    System.out.println(list);
    System.out.println(list2);
  }

  /**
   * 可重复执行器测试
   *
   * 只会一次预处理
   */
  @Test
  public void reuseTest() throws SQLException {
    ReuseExecutor executor = new ReuseExecutor(configuration, jdbcTransaction);
    MappedStatement ms = configuration.getMappedStatement("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById");
    List<Object> list = executor.doQuery(ms, 1, RowBounds.DEFAULT,
            SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));

    // 这里重复执行相同的SQL， 只会执行一次预处理  （从打印日志可以看出）
    List<Object> list2 = executor.doQuery(ms, 1, RowBounds.DEFAULT,
            SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(10));
  }

  /**
   * 批处理执行器测试
   *
   * 批处理执行器 针对 修改操作的  也就是增删改  一次预处理
   * 对于查询操作来说 和SimpleExecutor没什么区别
   *
   * 批处理操作必须手动刷新 就算是自动提交的事务处理器也要
   */
  @Test
  public void batchTest() throws SQLException {
    BatchExecutor executor = new BatchExecutor(configuration, jdbcTransaction);
    MappedStatement ms = configuration.getMappedStatement("cn.guosgbin.mybatis.example.mapper.UserMapper.updateNameById");
    Map map = new HashMap<>();
    map.put("name", "烧瓶");
    map.put("id", 1);

    // 只有一次预处理
    executor.doUpdate(ms, map);
    executor.doUpdate(ms, map);
    executor.doUpdate(ms, map); // 设置参数
    // 批处理刷新,刷新后才能提交
    executor.doFlushStatements(false);
  }


  /**
   * 基础执行器
   *
   * 测试走缓存
   */
  @Test
  public void baseExecutorTest() throws SQLException {
    Executor executor = new ReuseExecutor(configuration, jdbcTransaction);
    MappedStatement ms = configuration.getMappedStatement("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById");

    executor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    executor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
  }

  /**
   * 装饰者模式 缓存
   */
  @Test
  public void cacheExecutorTest() throws SQLException {
    Executor executor = new SimpleExecutor(configuration, jdbcTransaction);
    CachingExecutor cachingExecutor = new CachingExecutor(executor); // 二级缓存
    MappedStatement ms = configuration.getMappedStatement("cn.guosgbin.mybatis.example.mapper.UserMapper.selectById");

    cachingExecutor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);

    cachingExecutor.commit(true); // 只有加了这个 才会走二级缓存  因为二级缓存是跨线程的 需要提交完才能

    cachingExecutor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    cachingExecutor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
    cachingExecutor.query(ms, 1, RowBounds.DEFAULT, Executor.NO_RESULT_HANDLER);
  }
}
