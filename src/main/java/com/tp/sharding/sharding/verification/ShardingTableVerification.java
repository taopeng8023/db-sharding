package com.tp.sharding.sharding.verification;

import com.alibaba.druid.util.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.plugin.*;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.connection.ShardingConnection;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.context.ShardingRuntimeContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLSyntaxErrorException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:22:41
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
)})
public class ShardingTableVerification implements Interceptor {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    final String reg = ".*( col |.col |.col,|,col |,col,| col,|`col`| col=|.col=|,col=).*";
    final String seRegex = "\r|\n|\t|select\\s(.+)from\\s|\\sgroup\\s(.*)|\\sorder\\s(.*)";
    final String upRegex = "\r|\n|\t|update\\s|set\\s(.+)where\\s|\\sgroup\\s(.*)|\\sorder\\s(.*)";
    final String repEmpty = " ";


    public Object intercept(Invocation invocation) throws Throwable {
        this.verifyByInvocation(invocation);
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    private boolean verifyByInvocation(Invocation invocation) throws Exception {
        Object connectionAdapter = invocation.getArgs()[0];
        if (connectionAdapter instanceof Proxy) {
            connectionAdapter = this.getTarget(connectionAdapter);
        }

        if (!(connectionAdapter instanceof ShardingConnection)) {
            return true;
        } else {
            StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
            String sql = statementHandler.getBoundSql().getSql().trim().toLowerCase();
            if (!StringUtils.isEmpty(sql) && sql.length() >= 10) {
                String condition = this.getCondition(sql);
                ShardingConnection shardingConnection = (ShardingConnection)connectionAdapter;
                ShardingRuntimeContext runtimeContext = shardingConnection.getRuntimeContext();
                Collection<TableRule> tableRules = ((ShardingRule)runtimeContext.getRule()).getTableRules();
                Iterator var9 = tableRules.iterator();

                while(true) {
                    TableRule tableRule;
                    boolean isContain;
                    do {
                        do {
                            if (!var9.hasNext()) {
                                return true;
                            }

                            tableRule = (TableRule)var9.next();
                            isContain = this.contains(condition, tableRule.getLogicTable());
                        } while(!isContain);
                    } while(tableRule.getTableShardingStrategy() == null);

                    Collection<String> shardingColumns = tableRule.getTableShardingStrategy().getShardingColumns();
                    Iterator var13 = shardingColumns.iterator();

                    while(var13.hasNext()) {
                        String column = (String)var13.next();
                        if (!this.contains(condition, column)) {
                            throw new SQLSyntaxErrorException("sharding table [" + tableRule.getLogicTable() + "] not contains strategy column [" + column + "].");
                        }
                    }
                }
            } else {
                return true;
            }
        }
    }

    private Connection getTarget(Object proxy) throws Exception {
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        if (field == null) {
            field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        }

        field.setAccessible(true);
        ConnectionLogger connectionLogger = (ConnectionLogger)field.get(proxy);
        return connectionLogger.getConnection();
    }

    private boolean contains(String sql, String field) {
        String regex = ".*( col |.col |.col,|,col |,col,| col,|`col`| col=|.col=|,col=).*".replaceAll("col", field.toLowerCase());
        return sql.matches(regex);
    }

    private String getCondition(String sql) {
        String condition = "";
        if (!sql.startsWith("select") && !sql.startsWith("delete")) {
            if (sql.startsWith("update")) {
                condition = sql.replaceAll("\r|\n|\t|update\\s|set\\s(.+)where\\s|\\sgroup\\s(.*)|\\sorder\\s(.*)", " ");
            } else if (sql.startsWith("insert")) {
                condition = sql;
            }
        } else {
            condition = sql.replaceAll("\r|\n|\t|select\\s(.+)from\\s|\\sgroup\\s(.*)|\\sorder\\s(.*)", " ");
        }

        return " " + condition + " ";
    }
}

