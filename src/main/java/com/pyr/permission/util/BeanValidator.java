package com.pyr.permission.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pyr.permission.exception.ParamException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.*;

public class BeanValidator {

    /**
     * 校验失败，把失败的信息封装在map里返回
     *
     * @param t      校验的bean
     * @param groups 需要校验的数据
     * @param <T>    校验的bean类型
     * @return key-哪个字段有问题，value-对应的错误信息
     */
    public static <T> Map<String, String> validate(T t, Class... groups) {
        Set<ConstraintViolation<T>> validateResult = Validation.buildDefaultValidatorFactory().getValidator().validate(t, groups);
        if (validateResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap<String, String> errors = Maps.newLinkedHashMap();
            validateResult.forEach(rs -> {
                errors.put(rs.getPropertyPath().toString(), rs.getMessage());
            });
            return errors;
        }
    }

    /**
     * 校验list, 将list里的每个对象都进行校验
     *
     * @param collection 校验的list对象
     * @return list里所有对象的校验失败信息的map集合
     */
    public static Map<String, String> validateList(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map<String, String> errors;

        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object);
        } while (errors.isEmpty());

        return errors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validateList(Lists.asList(first, objects));
        } else {
            return validate(first);
        }
    }

    /**
     * 校验失败抛异常
     *
     * @param param
     * @throws ParamException
     */
    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            throw new ParamException(map.toString());
        }
    }
}
