package com.pyr.permission.controller;


import com.pyr.permission.enums.UserStatus;
import com.pyr.permission.model.SysUser;
import com.pyr.permission.service.SysUserService;
import com.pyr.permission.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/sys/user")
public class SysLoginController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = checkUser(username, password, sysUser);

        String ret = request.getParameter("ret");
        if (StringUtils.isBlank(errorMsg)) {
            // login success
            request.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                response.sendRedirect(ret);
            } else {
                response.sendRedirect("/admin/index.page"); //TODO
            }
            return;
        }

        request.setAttribute("error", errorMsg);
        request.setAttribute("username", username);
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        String path = "signin.jsp";
        request.getRequestDispatcher(path).forward(request, response);
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        String path = "signin.jsp";
        response.sendRedirect(path);
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
        if (!sysUser.getPassward().equals(MD5Util.encrypt(password))) {
            return "用户名或密码错误";
        }
        return !Objects.equals(sysUser.getStatus(), UserStatus.DISABLE.getCode()) ? "用户已被冻结，请联系管理员" : "";
    }
}