package com.pyr.permission.common.util;

import com.pyr.permission.common.excel.I18nText;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class ObjectUtil {

    public static final String EMPTY_TEXT = "空值";
    public static final String COMPARISON_RESULT_IF_ANY_OBJECT_NULL_TEMPLATE = "%s : %s";
    public static final String COMPARISON_RESULT_TEMPLATE = "%s : %s -> %s";

    public static <E> void ifPresent(E value, Consumer<E> consumer) {
        Optional.ofNullable(value).ifPresent(consumer);
    }

    public static <E, R> R ifPresent(E value, Function<E, R> mapping, R defaultValue) {
        return Optional.ofNullable(value).map(mapping).orElse(defaultValue);
    }

    public static <E, R> R ifPresent(E value, Function<E, R> mapping) {
        return ifPresent(value, mapping, null);
    }


    public static String compare(Object oldObj, Object newObj, Class<?> clazz) throws IllegalAccessException {
        List<String> changes = new ArrayList<>();
        if (ObjectUtils.allNotNull(oldObj, newObj) && oldObj.getClass() != newObj.getClass()) {
            return joinChanges(changes);
        }
        Field[] fields = clazz.getDeclaredFields();
        if (ObjectUtils.anyNull(oldObj, newObj)) {
            Object object = ObjectUtils.firstNonNull(oldObj, newObj);
            return getComparisonResultIfAnyObjectNull(object, changes, fields);
        }
        return getComparisonResult(oldObj, newObj, changes, fields);
    }

    private static String getComparisonResultIfAnyObjectNull(Object object, List<String> changes, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = defaultIfNull(field.get(object), EMPTY_TEXT);
            String message = String.format(COMPARISON_RESULT_IF_ANY_OBJECT_NULL_TEMPLATE, getFieldI18nValue(field), value);
            changes.add(message);
        }
        return joinChanges(changes);
    }

    private static String getComparisonResult(Object oldObj, Object newObj, List<String> changes, Field[] fields) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            Object oldValue = field.get(oldObj);
            Object newValue = field.get(newObj);
            if (!Objects.equals(oldValue, newValue)) {
                String message = String.format(COMPARISON_RESULT_TEMPLATE, getFieldI18nValue(field), oldValue, newValue);
                changes.add(message);
            }
        }
        return joinChanges(changes);
    }

    private static String joinChanges(List<String> changes) {
        return String.join("\n", changes);
    }

    public static String getFieldI18nValue(Field field) {
        I18nText i18nText = field.getAnnotation(I18nText.class);
        return ifPresent(i18nText, I18nText::value, field.getName());
    }
}
