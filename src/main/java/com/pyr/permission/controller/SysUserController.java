package com.pyr.permission.controller;


import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.model.SysUser;
import com.pyr.permission.param.UserParam;
import com.pyr.permission.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @RequestMapping("/save")
    @ResponseBody
    public ResultBody saveUser(UserParam param) {
        sysUserService.save(param);
        return ResultBody.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultBody updateUser(UserParam param) {
        sysUserService.update(param);
        return ResultBody.success();
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public ResultBody getUserInfo() {
        SysUser currentUser = RequestHolder.getCurrentUser();
        return ResultBody.success(currentUser);
    }
}
