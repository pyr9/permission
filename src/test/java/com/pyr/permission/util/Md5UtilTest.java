package com.pyr.permission.util;

import com.pyr.permission.common.util.MD5Util;
import org.junit.Test;

public class Md5UtilTest {
    @Test
    public void validateObject() {
        System.out.println(MD5Util.encrypt("111111"));
    }
}
