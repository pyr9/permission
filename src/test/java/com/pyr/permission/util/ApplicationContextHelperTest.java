package com.pyr.permission.util;

import com.pyr.permission.common.ApplicationContextHelper;
import com.pyr.permission.common.util.JsonMapper;
import com.pyr.permission.domain.role.mapper.SysAclModuleMapper;
import com.pyr.permission.domain.role.model.SysAclModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApplicationContextHelperTest {

    @Test
    public void test() {
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectById(1);
        log.info(JsonMapper.obj2String(module));
    }

}
