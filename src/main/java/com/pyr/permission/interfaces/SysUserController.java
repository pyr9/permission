package com.pyr.permission.interfaces;


import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.common.page.PageResult;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.param.UserParam;
import com.pyr.permission.domain.user.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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

    @RequestMapping("/pageUsers")
    @ResponseBody
    public ResultBody page(@RequestParam("departmentId") int deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.pageByDepartmentId(deptId, pageQuery);
        return ResultBody.success(result);
    }

    @RequestMapping("/users")
    @ResponseBody
    public ResultBody allUsers() {
        List<SysUser> users = sysUserService.getAllUser();
        return ResultBody.success(users);
    }
}
