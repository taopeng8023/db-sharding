package com.tp.sharding.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2023/02/07 15:09:27
 */
@Slf4j
public class SnoWalkerComplexShardingDB implements PreciseShardingAlgorithm {

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table's name
     */
    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        log.info("availableTargetNames:{} shardingValues:{}", availableTargetNames, shardingValue);
        String userId = shardingValue.getColumnName();
        int prefix = Math.abs(userId.hashCode()) % 2;
        String prefixStr = "ds_ms_"+prefix;
        Iterator var5 = availableTargetNames.iterator();
        String each;
        do {
            if (!var5.hasNext()) {
                throw new UnsupportedOperationException();
            }
            each = (String) var5.next();
        } while (!each.endsWith(prefixStr));
        log.info("DB ========:{}",each);
        return each;
    }
}
