package com.pyr.permission.filter;

import com.pyr.permission.common.ApplicationContextHelper;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.exception.BizException;
import com.pyr.permission.common.util.JsonMapper;
import com.pyr.permission.domain.role.service.SysCoreService;
import com.pyr.permission.domain.user.model.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.pyr.permission.common.exception.CommonEnum.FORBIDDEN;

/**
 * 权限url拦截
 */
@Slf4j
public class AclControlFilter implements HandlerInterceptor {

    /**
     * 用户没登录或者没权限，都应该跳转至403页面
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();
        Map<String, String[]> requestMap = request.getParameterMap();
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            log.info("someone visit {}, but no login, parameter:{}", requestURI, JsonMapper.obj2String(requestMap));
            throw new BizException(FORBIDDEN);
        }
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        assert sysCoreService != null;
        if (!sysCoreService.hasUrlAcl(requestURI)) {
            log.info("{} visit {}, but no login, parameter:{}", JsonMapper.obj2String(sysUser), requestURI, JsonMapper.obj2String(requestMap));
            throw new BizException(FORBIDDEN);
        }
        return true;
    }
}
