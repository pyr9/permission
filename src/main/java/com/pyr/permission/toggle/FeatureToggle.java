package com.pyr.permission.toggle;

import java.util.Random;

public class FeatureToggle {

    // 是否启用灰度功能（全局开关）
    private static final boolean GRAYSCALE_ENABLED = true;

    // 灰度百分比（例如：10% 的用户可以访问新功能）
    private static final int GRAYSCALE_PERCENTAGE = 10;

    /**
     * 根据雪花算法生成的用户ID判断是否启用新功能
     *
     * @param userId 雪花算法生成的用户ID
     * @return 如果满足灰度规则，则返回true
     */
    public static boolean isNewFeatureEnabled(long userId) {
        if (!GRAYSCALE_ENABLED) {
            return false; // 全局开关关闭时直接返回false
        }

        // 使用用户ID取模，确保分配均匀
        int hash = Math.abs((int) (userId % 100));
        return hash < GRAYSCALE_PERCENTAGE;
    }

    /**
     * 根据随机流量分配判断是否启用新功能
     *
     * @return 如果满足灰度规则，则返回true
     */
    public static boolean isNewFeatureEnabledByRandom() {
        if (!GRAYSCALE_ENABLED) {
            return false; // 全局开关关闭时直接返回false
        }

        Random random = new Random();
        return random.nextInt(100) < GRAYSCALE_PERCENTAGE;
    }
}