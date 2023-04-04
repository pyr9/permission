package com.pyr.permission.filter;

import com.google.common.collect.Sets;
import com.pyr.permission.common.ApplicationContextHelper;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.exception.BizException;
import com.pyr.permission.common.util.JsonMapper;
import com.pyr.permission.domain.role.service.SysCoreService;
import com.pyr.permission.domain.user.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.pyr.permission.common.exception.CommonEnum.FORBIDDEN;

/**
 * 权限url拦截
 */
@Slf4j
public class AclControlFilter implements Filter {


    // url白名单,即：不需要拦截的URL
    private static final Set<String> exclusionUrlSet = Sets.newHashSet();

    @Override
    public void init(FilterConfig filterConfig) {
        exclusionUrlSet.add("/sys/user/login");
    }

    /**
     * 用户没登录或者没权限，都应该跳转至403页面
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        Map<String, String[]> requestMap = request.getParameterMap();

        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            log.info("someone visit {}, but no login, parameter:{}", servletPath, JsonMapper.obj2String(requestMap));
            //没有权限，跳转至403界面。前端捕获403
            throw new BizException(FORBIDDEN);
        }
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        assert sysCoreService != null;
        if (!sysCoreService.hasUrlAcl(servletPath)) {
            log.info("{} visit {}, but no login, parameter:{}", JsonMapper.obj2String(sysUser), servletPath, JsonMapper.obj2String(requestMap));
            throw new BizException(FORBIDDEN);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {

    }
}
