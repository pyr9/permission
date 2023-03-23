package com.pyr.permission.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.util.IpUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SysMetaObjectHandler implements MetaObjectHandler {
    //使用mp实现添加操作,这个方法会执行,metaObject元数据(表中的名字,表中的字段)
    @Override
    public void insertFill(MetaObject metaObject) {
        //根据名称设置属性值
        this.setFieldValByName("creatorId", RequestHolder.getCurrentUser().getId(), metaObject);
        this.setFieldValByName("creatorIp", IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    //使用mp实现修改操作,这个方法会执行
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
