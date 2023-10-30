package com.pyr.permission.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class ApplicationContextHelperTest {

    @Test
    public void test() {
        //SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        //SysAclModule module = moduleMapper.selectById(1);
        //log.info(JsonMapper.obj2String(module));
        String url = "em-admin/emAclUser?id=406612,405222";
        String regex = "em-admin/emAclUser" + "\\?id=\\d+(,\\d+)*";
        if (url.matches(regex)) {
            System.out.println("匹配成功");
        } else {
            System.out.println("不匹配");
        }
    }


}
