package com.pyr.permission.common.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

public class SysIdWorker {
    public static Long generateId() {
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        return (Long) identifierGenerator.nextId(new Object());
    }
}
