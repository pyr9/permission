package com.pyr.permission.util;

import com.pyr.permission.common.util.ObjectUtil;
import com.pyr.permission.util.excel.SysUserVo;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectUtilTest {

    @Test
    public void testDataChange() throws IllegalAccessException {
        SysUserVo oldUser = new SysUserVo();
        oldUser.setTelephone("13202222222");
        oldUser.setPassword("111");
        oldUser.setUserName("张三");
        oldUser.setStatus(1);
        SysUserVo newUser = new SysUserVo();
        newUser.setTelephone("13202222288");
        newUser.setPassword("111");
        newUser.setUserName("张三12");
        newUser.setStatus(1);
        String result = ObjectUtil.compare(oldUser, newUser, SysUserVo.class);
        String expected = "用户名 : 张三 -> 张三12\n" +
                "电话号码 : 13202222222 -> 13202222288";
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void testDataAdd() throws IllegalAccessException {
        SysUserVo newUser = new SysUserVo();
        newUser.setTelephone("13202222288");
        newUser.setUserName("张三");
        String result = ObjectUtil.compare(null, newUser, SysUserVo.class);
        String expected = "用户名 : 张三\n" +
                "电话号码 : 13202222288\n" +
                "邮箱 : 空值\n" +
                "password : 空值\n" +
                "departmentId : 空值\n" +
                "status : 空值";
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void testDataDelete() throws IllegalAccessException {
        SysUserVo newUser = new SysUserVo();
        newUser.setTelephone("13202222288");
        newUser.setUserName("张三");
        String result = ObjectUtil.compare(newUser, null, SysUserVo.class);
        String expected = "用户名 : 张三\n" +
                "电话号码 : 13202222288\n" +
                "邮箱 : 空值\n" +
                "password : 空值\n" +
                "departmentId : 空值\n" +
                "status : 空值";
        System.out.println(result);
        assertEquals(expected, result);
    }
}
