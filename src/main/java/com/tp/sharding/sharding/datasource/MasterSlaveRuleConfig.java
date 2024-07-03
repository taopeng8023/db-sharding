package com.tp.sharding.sharding.datasource;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.shardingsphere.api.config.RuleConfiguration;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;

import java.util.Collection;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:03:48
 */
public final class MasterSlaveRuleConfig implements RuleConfiguration {
    private String name;
    private String masterDataSourceName;
    private Collection<String> slaveDataSourceNames;
    private String loadBalanceStrategy;

    public MasterSlaveRuleConfig() {
    }

    public MasterSlaveRuleConfig(final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames) {
        this(name, masterDataSourceName, slaveDataSourceNames, (String)null);
    }

    public MasterSlaveRuleConfig(final String name, final String masterDataSourceName, final Collection<String> slaveDataSourceNames, final String loadBalanceStrategy) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "Name is required.");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(masterDataSourceName), "MasterDataSourceName is required.");
        Preconditions.checkArgument(null != slaveDataSourceNames && !slaveDataSourceNames.isEmpty(), "SlaveDataSourceNames is required.");
        this.name = name;
        this.masterDataSourceName = masterDataSourceName;
        this.slaveDataSourceNames = slaveDataSourceNames;
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    public MasterSlaveRuleConfiguration parseMasterSlaveRule() {
        MasterSlaveRuleConfiguration config = new MasterSlaveRuleConfiguration(this.name, this.masterDataSourceName, this.slaveDataSourceNames, new LoadBalanceStrategyConfiguration(this.loadBalanceStrategy));
        return config;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMasterDataSourceName(String masterDataSourceName) {
        this.masterDataSourceName = masterDataSourceName;
    }

    public void setSlaveDataSourceNames(Collection<String> slaveDataSourceNames) {
        this.slaveDataSourceNames = slaveDataSourceNames;
    }

    public void setLoadBalanceStrategy(String loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    public String getName() {
        return this.name;
    }

    public String getMasterDataSourceName() {
        return this.masterDataSourceName;
    }

    public Collection<String> getSlaveDataSourceNames() {
        return this.slaveDataSourceNames;
    }

    public String getLoadBalanceStrategy() {
        return this.loadBalanceStrategy;
    }
}
