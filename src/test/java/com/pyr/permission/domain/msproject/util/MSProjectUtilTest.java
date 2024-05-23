package com.pyr.permission.domain.msproject.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class MSProjectUtilTest {

    public static void main(String[] args) throws IOException {
        String property = System.getProperty("user.dir");
        String path = property + "/src/main/resources/file/vjpdEguJD2.mpp";
        System.out.println(Files.probeContentType(Paths.get(path)));
    }
}