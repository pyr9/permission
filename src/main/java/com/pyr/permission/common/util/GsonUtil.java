package com.pyr.permission.common.util;

import com.google.gson.GsonBuilder;
import com.pyr.permission.common.excel.VoI18nStrategy;
import lombok.val;

/**
 * excel导出工具类，可以把对象重新构建json对象，如：
 * {"用户名":"张三","电话号码":"12222222222","邮箱":"1@qq.com","password":"111"}
 */
public class GsonUtil {
    public static String voToI18nText(Object object) {
        val gson = new GsonBuilder()
                .setFieldNamingStrategy(new VoI18nStrategy())
                .create();
        return gson.toJson(object);
    }

    public static String voToI18nText(Object object, String customVuexName) {
        val gson = new GsonBuilder()
                .setFieldNamingStrategy(new VoI18nStrategy())
                .setExclusionStrategies(new VoExclusionStrategy(customVuexName))
                .create();
        return gson.toJson(object);
    }
}
