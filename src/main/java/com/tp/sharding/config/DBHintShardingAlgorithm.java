package com.tp.sharding.config;

import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.Collection;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:34:57
 */
public class DBHintShardingAlgorithm implements HintShardingAlgorithm {
    /**
     * Sharding.
     *
     * <p>sharding value injected by hint, not in SQL.</p>
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data sources or tables's names
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, HintShardingValue shardingValue) {
        return null;
    }
}
