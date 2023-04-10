package com.pyr.permission.util;

import com.pyr.permission.common.util.GsonUtil;
import com.pyr.permission.util.excel.SysUserVo;
import lombok.val;
import org.junit.Test;

public class GsonUtilTest {
    @Test
    public void testToJson() {
        SysUserVo user = new SysUserVo();
        user.setUserName("张三");
        user.setTelephone("12222222222");
        user.setMail("1@qq.com");
        user.setPassword("111");
        val s = GsonUtil.voToI18nText(user);
        System.out.println(s);
    }
}
