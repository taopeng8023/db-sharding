package com.tp.sharding.config;

import com.tp.sharding.sharding.algorithm.PreciseShardingAlgorithmTB;
import com.tp.sharding.sharding.algorithm.SnoWalkerComplexShardingDB;
import com.tp.sharding.sharding.config.DatabaseShardingConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:24:35
 */
@Slf4j
@Configuration
public class DataSourceConfig extends DatabaseShardingConfigurator {

    @Override
    public Collection<TableRuleConfiguration> tableRuleConfigs() {
        List<TableRuleConfiguration> tableRuleConfigurations = new LinkedList<>();
        TableRuleConfiguration userInfRule = new TableRuleConfiguration("user_info",UserInfoDataNode.getUserInfoPeriodDataNodes("user_info",23,24));
        userInfRule.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("userId",new SnoWalkerComplexShardingDB()));
        userInfRule.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("userId",new PreciseShardingAlgorithmTB()));
        log.info("actualDataNodes================:{}",userInfRule.getActualDataNodes());
        tableRuleConfigurations.add(userInfRule);
        return tableRuleConfigurations;
    }

    @Override
    public Collection<String> bindingTableGroups() {
        List<String> bindingTableGroups = new LinkedList<>();
        bindingTableGroups.add("user_info");
        return bindingTableGroups;
    }
}
