package com.pyr.permission.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {
    public static List<Integer> splitToIntList(String str) {
        List<String> strList = Arrays.asList(str.split(","));
        return strList.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
