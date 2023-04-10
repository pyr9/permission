package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.param.SysRoleParam;
import com.pyr.permission.domain.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

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
}
