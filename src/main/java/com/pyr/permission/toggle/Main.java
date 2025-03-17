package com.pyr.permission.toggle;

public class Main {
    public static void main(String[] args) {
        FeatureService featureService = new FeatureService();

        // 模拟多个用户访问（假设这些是雪花算法生成的ID）
        long[] userIDs = {123456789012345678L, 234567890123456789L, 345678901234567890L, 456789012345678901L};

        //for (long userId : userIDs) {
        //    System.out.println("用户ID: " + userId);
        //    featureService.executeFeature(userId);
        //    System.out.println("-------------------------");
        //}


    }
}