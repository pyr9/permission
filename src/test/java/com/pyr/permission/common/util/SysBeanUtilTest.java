package com.pyr.permission.common.util;

import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.param.UserParam;
import org.junit.jupiter.api.Test;

class SysBeanUtilTest {

    @Test
    void convert() {
        UserParam userParam = new UserParam();
        userParam.setId(1);
        userParam.setUserName("张三");
        userParam.setMail("1@qq.com");
        SysUser user = (SysUser) SysBeanUtil.convert(userParam, SysUser.class);
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getMail());
    }
}