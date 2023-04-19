package com.pyr.permission.common.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.pyr.permission.common.excel.I18nText;
import lombok.val;

import java.util.Arrays;

public class VoExclusionStrategy implements ExclusionStrategy {
    private String customVuexName;

    public VoExclusionStrategy(String customVuexName) {
        this.customVuexName = customVuexName;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        I18nText annotation = fieldAttributes.getAnnotation(I18nText.class);
        if (annotation == null) {
            return false;
        }
        val customVuex = annotation.customVuex();
        if (customVuex == null) {
            return false;
        }
        return !Arrays.asList(customVuex).contains(customVuexName);
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
