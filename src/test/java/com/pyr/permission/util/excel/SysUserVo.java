package com.pyr.permission.util.excel;

import com.pyr.permission.common.excel.I18nText;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class SysUserVo {
    @I18nText(value = "用户名", customVuex = {"sith", "vendor"})
    private String userName;

    @I18nText(value = "电话号码", customVuex = {"sith"})
    private String telephone;

    @I18nText(value = "邮箱", customVuex = {"sith", "vendor"})
    private String mail;

    private String password;

    private Integer departmentId;

    private Integer status;

    private Date birthday;


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        DateTimeConverter dateConverter = new DateConverter(null);
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"});
        ConvertUtils.register(dateConverter, Date.class);

        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("userName", "John Smith");
        dataMap.put("birthday", "1990-01-01T00:00:00.000Z");

        SysUserVo user = new SysUserVo();
        BeanUtils.populate(user, dataMap);
        System.out.println(user.getUserName());
        System.out.println(user.getBirthday());
    }
}
