package com.tp.sharding.sharding.config;

import com.tp.sharding.sharding.datasource.DefaultDataSource;
import com.tp.sharding.sharding.datasource.MasterSlaveRuleConfig;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:21:26
 */
public class DatabaseMasterSlaveConfigurator extends AbstractDatabaseConfigSupport {

    public DataSource dataSource(DefaultDataSource defaultDataSource) throws SQLException {
        log.info("Initialize master-slave dataSource");
        Map<String, DataSource> dataSourceMap = new HashMap();
        dataSourceMap.putAll(defaultDataSource.getDataSources());
        MasterSlaveRuleConfig config = (MasterSlaveRuleConfig)defaultDataSource.getMasterSlaveRules().get(0);
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration(config.getName(), config.getMasterDataSourceName(), config.getSlaveDataSourceNames());
        Properties properties = new Properties();
        properties.setProperty("sql.show", this.sqlShow);
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, properties);
    }
}
