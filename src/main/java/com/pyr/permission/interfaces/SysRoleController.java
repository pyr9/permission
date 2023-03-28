package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.param.SysRoleParam;
import com.pyr.permission.domain.role.service.SysRoleAclService;
import com.pyr.permission.domain.role.service.SysRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleAclService sysRoleAclService;

    @RequestMapping("/add")
    @ResponseBody
    public ResultBody saveRole(SysRoleParam param) {
        sysRoleService.insert(param);
        return ResultBody.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultBody updateRole(SysRoleParam param) {
        sysRoleService.update(param);
        return ResultBody.success();
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultBody list() {
        return ResultBody.success(sysRoleService.getAll());
    }

    @DeleteMapping
    public ResultBody deleteById(Integer id) {
        return ResultBody.success(sysRoleService.deleteById(id));
    }

    @RequestMapping("/roleTree")
    @ResponseBody
    public ResultBody roleTree(@RequestParam("roleId") int roleId) {
        return ResultBody.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 设置权限模块下的权限点
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public ResultBody changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false) List<Integer> aclIds) {
        sysRoleAclService.changeRoleAcls(roleId, aclIds);
        return ResultBody.success();
    }
}
