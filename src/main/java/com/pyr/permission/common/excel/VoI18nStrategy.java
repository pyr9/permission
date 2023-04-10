package com.pyr.permission.common.excel;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class VoI18nStrategy implements FieldNamingStrategy {
    @Override
    public String translateName(Field field) {
        I18nText annotation = field.getAnnotation(I18nText.class);
        if (annotation != null) {
            return annotation.value();
        }
        return field.getName();
    }
}
