package com.tp.sharding.sharding.datasource;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:03:22
 */
public class DefaultDataSource {
    private String defaultDataSourceName;
    private Map<String, DruidDataSource> dataSources = new HashMap();
    private List<MasterSlaveRuleConfig> masterSlaveRules = new ArrayList();

    public DefaultDataSource() {
    }

    public String getDefaultDataSourceName() {
        return this.defaultDataSourceName;
    }

    public void setDefaultDataSourceName(String defaultDataSourceName) {
        this.defaultDataSourceName = defaultDataSourceName;
    }

    public Map<String, DruidDataSource> getDataSources() {
        return this.dataSources;
    }

    public void setDataSources(Map<String, DruidDataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public List<MasterSlaveRuleConfig> getMasterSlaveRules() {
        return this.masterSlaveRules;
    }

    public void setMasterSlaveRules(List<MasterSlaveRuleConfig> masterSlaveRules) {
        this.masterSlaveRules = masterSlaveRules;
    }
}
