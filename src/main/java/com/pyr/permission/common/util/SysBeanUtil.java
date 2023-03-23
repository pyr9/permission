package com.pyr.permission.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class SysBeanUtil {
    public static <T> Object convert(Object source, Class<T> tClass) {
        try {
            Object target = tClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
