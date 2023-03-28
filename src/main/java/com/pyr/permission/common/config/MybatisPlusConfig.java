package com.pyr.permission.common.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.pyr.permission.common.handler.SysMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {
    /**
     * MyBatis 字段值自动填充
     *
     * @return
     */
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        //加载数据源
        mybatisPlus.setDataSource(dataSource);
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        //配置填充器
        globalConfig.setMetaObjectHandler(new SysMetaObjectHandler());
        mybatisPlus.setGlobalConfig(globalConfig);
        return mybatisPlus;
    }
}
