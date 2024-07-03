package com.tp.sharding.sharding.config;

import com.tp.sharding.sharding.datasource.DefaultDataSource;
import com.tp.sharding.sharding.datasource.MasterSlaveRuleConfig;
import com.tp.sharding.sharding.verification.ShardingTableVerification;
import org.apache.shardingsphere.api.config.encrypt.EncryptRuleConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:21:53
 */
public class DatabaseShardingConfigurator extends AbstractDatabaseConfigSupport {

    @Bean
    public ShardingTableVerification shardingTableVerification() {
        return new ShardingTableVerification();
    }

    public DataSource dataSource(DefaultDataSource defaultDataSource) throws SQLException {
        log.info("Initialize sharding dataSource");
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setTableRuleConfigs(this.tableRuleConfigs());
        shardingRuleConfig.setEncryptRuleConfig(this.encryptRuleConfig());
        shardingRuleConfig.setBindingTableGroups(this.bindingTableGroups());
        shardingRuleConfig.setBroadcastTables(this.broadcastTables());
        shardingRuleConfig.setDefaultDataSourceName(defaultDataSource.getDefaultDataSourceName());
        Collection<MasterSlaveRuleConfiguration> masterSlaveRuleConfigs = new LinkedList();
        Iterator var4 = defaultDataSource.getMasterSlaveRules().iterator();

        while(var4.hasNext()) {
            MasterSlaveRuleConfig config = (MasterSlaveRuleConfig)var4.next();
            masterSlaveRuleConfigs.add(config.parseMasterSlaveRule());
        }

        shardingRuleConfig.setMasterSlaveRuleConfigs(masterSlaveRuleConfigs);
        Map<String, DataSource> dataSourceMap = new HashMap();
        dataSourceMap.putAll(defaultDataSource.getDataSources());
        Properties properties = new Properties();
        properties.setProperty("sql.show", this.sqlShow);
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
    }

    public Collection<TableRuleConfiguration> tableRuleConfigs() {
        return Collections.emptyList();
    }

    public EncryptRuleConfiguration encryptRuleConfig() {
        return null;
    }

    public Collection<String> broadcastTables() {
        return Collections.emptyList();
    }

    public Collection<String> bindingTableGroups() {
        return Collections.emptyList();
    }
}
