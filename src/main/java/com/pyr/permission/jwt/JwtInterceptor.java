package com.pyr.permission.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.pyr.permission.common.ApplicationContextHelper;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.exception.BizException;
import com.pyr.permission.model.SysUser;
import com.pyr.permission.service.SysUserService;
import com.pyr.permission.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.pyr.permission.util.JWTUtil.USER_ID;

/**
 * 授权Filter
 * http过滤器，把request和user放入ThreadLocal
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    public static final String AUTHORIZATION_HEADER = "authorization";
    public static final String OPTIONS = "OPTIONS";
    public static final String UTF_8 = "UTF-8";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setCharacterEncoding(UTF_8);
        //获取 header里的token
        final String token = request.getHeader(AUTHORIZATION_HEADER);
        if (OPTIONS.equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // Except OPTIONS, other request should be checked by JWT
        else {
            if (token == null) {
                throw new BizException("请先登录");
            }
            Map<String, Claim> userData = JWTUtil.verifyToken(token);
            if (userData == null) {
                throw new BizException("token验证失败");
            }
            Integer userId = userData.get(USER_ID).asInt();

            //拦截器 拿到用户信息，放到RequestHolder中
            SysUserService sysUserService = ApplicationContextHelper.popBean(SysUserService.class);
            assert sysUserService != null;
            SysUser currentUser = sysUserService.findById(userId);
            RequestHolder.add(currentUser);
            RequestHolder.add(request);

            // 更新token有效时间 (如果需要更新其实就是产生一个新的token), 或者可以不更新，让用户重新登录
            //if (JWTUtil.isNeedUpdate(token)) {
            //    String newToken = JWTUtil.createToken(token);
            //    response.setHeader(JWTUtil.USER_LOGIN_TOKEN, newToken);
            //}
            return true;
        }
    }
}
