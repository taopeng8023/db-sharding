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
 * @date 2024/07/02 18:40:44
 */
@Slf4j
public class PreciseShardingAlgorithmTB implements PreciseShardingAlgorithm {
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
        //time
        String userId = (String) shardingValue.getValue();
        int userIdLength = userId.length();
        // 根据表前缀、所在地区和分片得到实际表
        String shardFix = userId.substring(userIdLength - 4, userIdLength - 2);
        log.info("shardFix=============:{}",shardFix);
        Iterator var5 = availableTargetNames.iterator();

        String each;
        do {
            if (!var5.hasNext()) {
                throw new UnsupportedOperationException();
            }

            each = (String) var5.next();
        } while (!each.endsWith(shardFix));

        return each;
    }
}
