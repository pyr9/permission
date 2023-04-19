package com.pyr.permission.util;

import com.pyr.permission.common.util.GsonUtil;
import com.pyr.permission.util.excel.SysUserVo;
import lombok.val;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GsonUtilTest {
    @Test
    public void testToJson() {
        SysUserVo user = new SysUserVo();
        user.setUserName("张三");
        user.setTelephone("12222222222");
        user.setMail("1@qq.com");
        user.setPassword("111");

        val json = GsonUtil.voToI18nText(user);
        String expected = "{\"用户名\":\"张三\",\"电话号码\":\"12222222222\",\"邮箱\":\"1@qq.com\",\"password\":\"111\"}";
        assertEquals(expected, json);
        System.out.println(json);
    }

    @Test
    public void testToJsonWithCustomVuexName() {
        SysUserVo user = new SysUserVo();
        user.setUserName("张三");
        user.setTelephone("12222222222");
        user.setMail("1@qq.com");
        user.setPassword("111");

        String customVuexName = "vendor";
        val json = GsonUtil.voToI18nText(user, customVuexName);
        String expected = "{\"用户名\":\"张三\",\"邮箱\":\"1@qq.com\",\"password\":\"111\"}";
        assertEquals(expected, json);
        System.out.println(json);
    }
}
