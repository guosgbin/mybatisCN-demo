package cn.guosgbin.mybatis.example.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author: Dylan kwok GSGB
 * @date: 2021/6/21 22:12
 * <p>
 * 古之立大事者，不惟有超世之才，亦必有坚忍不拔之志——苏轼
 */
@Intercepts(@Signature(
        // 要拦截的类
        type = StatementHandler.class,
        // 要拦截的类中的拦截的方法
        method = "prepare",
        // 拦截方法的入参的类型
        args = {Connection.class, Integer.class}
))
public class SimpleIntercept implements Interceptor {
    private String beforeInfo;
    private String afterInfo;

    /**
     * 拦截方法做的事
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("拦截prepare方法之前: " + beforeInfo);
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();
        System.out.println("此次要执行的SQL是 --> " + sql);
        // 执行原有方法
        Object proceed = invocation.proceed();
        System.out.println("拦截prepare方法之后: " + afterInfo);
        return proceed;
    }

//    @Override
//    public Object plugin(Object target) {
//        return null;
//    }

    /**
     * 赋值属性
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        beforeInfo = String.valueOf(properties.get("beforeInfo"));
        afterInfo = String.valueOf(properties.get("afterInfo"));
    }
}
