package com.pyr.permission.util;

import com.pyr.permission.common.util.JWTUtil;
import com.pyr.permission.domain.user.model.SysUser;
import lombok.val;
import org.junit.Test;

import java.util.Date;

import static com.pyr.permission.common.util.JWTUtil.EXPIRATION;

public class JwtUtilTest {

    public static void main(String[] args) {
        System.out.println(new Date(System.currentTimeMillis() + EXPIRATION));
    }

    @Test
    public void createToken() {
        SysUser sysUser = new SysUser();
        sysUser.setId(1642898650068758530L);
        val token = JWTUtil.createToken(sysUser);
        System.out.println(token);
    }

}
