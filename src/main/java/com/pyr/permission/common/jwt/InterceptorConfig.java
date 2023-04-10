package com.pyr.permission.common.jwt;

import com.pyr.permission.filter.AclControlFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 未登录的用户拦截
        registry.addInterceptor(new JwtInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/sys/user/login", "/file/**");
        // 用户权限拦截
        registry.addInterceptor(new AclControlFilter())
                .addPathPatterns("/**")
                .excludePathPatterns("/sys/user/login", "/file/**");
    }
}