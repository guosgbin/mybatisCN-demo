package cn.guosgbin.mybatis.example;

import cn.guosgbin.mybatis.example.entity.User;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
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
public class SqlNodeTest {
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
   * 测试if标签
   */
  @Test
  public void testIfNode() {
    User user = new User();
    user.setId(1);

    DynamicContext dynamicContext = new DynamicContext(configuration, user);
    new StaticTextSqlNode("select * from tb_user").apply(dynamicContext);

    IfSqlNode ifSqlNode = new IfSqlNode(new StaticTextSqlNode("id = #{id}"), "id != null");
    ifSqlNode.apply(dynamicContext);

    System.out.println(dynamicContext.getSql());

  }
}
