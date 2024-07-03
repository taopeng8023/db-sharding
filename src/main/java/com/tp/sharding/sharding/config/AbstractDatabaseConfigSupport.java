package com.tp.sharding.sharding.config;

import com.tp.sharding.sharding.aspectj.HintMasterRouteAspect;
import com.tp.sharding.sharding.datasource.DefaultDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:02:31
 */
public abstract class AbstractDatabaseConfigSupport {
    protected static final Logger log = LoggerFactory.getLogger(AbstractDatabaseConfigSupport.class);
    @Value("${sharding.jdbc.sql.show: true}")
    protected String sqlShow;

    public AbstractDatabaseConfigSupport() {
    }

    @Bean(
            name = {"defaultDataSource"}
    )
    @ConfigurationProperties(
            prefix = "sharding.jdbc"
    )
    public DefaultDataSource defaultDataSource() {
        return new DefaultDataSource();
    }

    @Primary
    @Bean(
            name = {"dataSource"}
    )
    @ConditionalOnBean(
            name = {"defaultDataSource"}
    )
    public abstract DataSource dataSource(@Qualifier("defaultDataSource") DefaultDataSource defaultDataSource) throws SQLException;

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        return platformTransactionManager;
    }

    @Bean
    public HintMasterRouteAspect hintMasterRouteAspect() {
        return new HintMasterRouteAspect();
    }
}