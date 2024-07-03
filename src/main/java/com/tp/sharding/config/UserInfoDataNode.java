package com.tp.sharding.config;

/**
 * @author taopeng
 * @version 1.0
 * @Description
 * @date 2024/07/02 18:28:47
 */
public class UserInfoDataNode {
    public static String getUserInfoPeriodDataNodes(String tableFix, int startYear, int endYear) {
        StringBuilder stringBuilder = new StringBuilder(30);
        for (int a = 0; a <= 1; a++) {
            for (int i = startYear; i <= endYear; ++i) {
                for (int j = 0; j <= 5; ++j) {
                    String table = String.format("ds_ms_%s.%s_%s_%02d", a, tableFix, i, j);
                    stringBuilder.append(table);
                    stringBuilder.append(",");
                }
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() -1 );
        return stringBuilder.toString();
    }
}
