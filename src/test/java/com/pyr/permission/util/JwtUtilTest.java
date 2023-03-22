package com.pyr.permission.util;

import java.util.Date;

import static com.pyr.permission.common.util.JWTUtil.EXPIRATION;

public class JwtUtilTest {

    public static void main(String[] args) {
        System.out.println(new Date(System.currentTimeMillis() + EXPIRATION));
    }
}
