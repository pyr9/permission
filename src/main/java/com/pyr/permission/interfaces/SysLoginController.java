package com.pyr.permission.interfaces;


import com.pyr.permission.common.ResultBody;
import com.pyr.permission.common.util.JWTUtil;
import com.pyr.permission.common.util.MD5Util;
import com.pyr.permission.domain.user.enums.UserStatus;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.param.LoginParam;
import com.pyr.permission.domain.user.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/sys/user")
public class SysLoginController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("/login")
    public ResultBody login(@RequestBody LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = checkUser(username, password, sysUser);

        if (StringUtils.isBlank(errorMsg)) {
            String jwtToken = JWTUtil.createToken(sysUser);
            return ResultBody.success(jwtToken);
        }
        return ResultBody.error(errorMsg);
    }

    @RequestMapping("/logout")
    public ResultBody logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 需要返回code200
        request.getSession().invalidate();
        return ResultBody.success();
    }

    private String checkUser(String username, String password, SysUser sysUser) {
        if (StringUtils.isBlank(username)) {
            return "用户名不可以为空";
        }
        if (StringUtils.isBlank(password)) {
            return "密码不可以为空";
        }
        if (sysUser == null) {
            return "查询不到指定的用户";
        }
        if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            return "用户名或密码错误";
        }
        return Objects.equals(sysUser.getStatus(), UserStatus.DISABLE.getCode()) ? "用户已被冻结，请联系管理员" : "";
    }

}