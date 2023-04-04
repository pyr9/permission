package com.pyr.permission.interfaces;


import com.google.common.collect.Maps;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.common.page.PageResult;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.service.SysRoleService;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.param.UserParam;
import com.pyr.permission.domain.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/save")
    @ResponseBody
    public ResultBody saveUser(@RequestBody UserParam param) {
        sysUserService.save(param);
        return ResultBody.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultBody updateUser(@RequestBody UserParam param) {
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
    public ResultBody page(@RequestParam("departmentId") Long deptId, PageQuery pageQuery) {
        PageResult<SysUser> result = sysUserService.pageByDepartmentId(deptId, pageQuery);
        return ResultBody.success(result);
    }

    @RequestMapping("/users")
    @ResponseBody
    public ResultBody allUsers() {
        List<SysUser> users = sysUserService.getAll();
        return ResultBody.success(users);
    }

    /**
     * - 查询单个用户下的所有权限点 -------> todo
     * - 查询单个用户下的所有角色 -------> todo
     */
    @RequestMapping("/aclsAndRoles")
    @ResponseBody
    public ResultBody aclsAndRoles(@RequestParam("userId") long userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getRoleListByUserId(userId));
        return ResultBody.success(map);
    }
}
