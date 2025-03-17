package com.pyr.permission.toggle;

public class FeatureService {

    /**
     * 执行功能逻辑
     *
     * @param userId 雪花算法生成的用户ID
     */
    public void executeFeature(long userId) {
        if (FeatureToggle.isNewFeatureEnabled(userId)) {
            // 执行新功能
            System.out.println("执行新功能...");
            newFeatureImplementation();
        } else {
            // 执行旧功能
            System.out.println("执行旧功能...");
            oldFeatureImplementation();
        }
    }

    private void newFeatureImplementation() {
        // 新功能的实现逻辑
        System.out.println("这是新功能的实现逻辑！");
    }

    private void oldFeatureImplementation() {
        // 旧功能的实现逻辑
        System.out.println("这是旧功能的实现逻辑！");
    }
}