package com.tp.sharding.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2023/02/07 15:24:49
 */
@Slf4j
public class SnoWalkerComplexShardingTB implements ComplexKeysShardingAlgorithm {
    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding results for data sources or tables's names
     */
    @Override
    public Collection< String > doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        log.info("availableTargetNames:{} shardingValues:{}", availableTargetNames, shardingValue);
        //time
        LocalDateTime timeDate = (LocalDateTime) ((Collection) shardingValue.getColumnNameAndShardingValuesMap().get("createTime")).toArray()[0];
        String timeStr = format(timeDate,"yyyMMdd");
        String userId = (String) ((Collection) shardingValue.getColumnNameAndShardingValuesMap().get("userId")).toArray()[0];
        int userIdLength = userId.length();
        // 根据表前缀、所在地区和分片得到实际表
        String shardFix = timeStr.substring(2, 6) + "_" + "00" + userId.substring(userIdLength - 4, userIdLength - 2);
        Iterator var5 = availableTargetNames.iterator();

        String each;
        do {
            if (!var5.hasNext()) {
                throw new UnsupportedOperationException();
            }

            each = (String) var5.next();
        } while (!each.endsWith(shardFix));

        return Arrays.asList(each);
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(localDateTime.withNano(0));
    }
}
