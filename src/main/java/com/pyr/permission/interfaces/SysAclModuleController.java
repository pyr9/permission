package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.param.SysAclModuleParam;
import com.pyr.permission.domain.role.service.SysAclModuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysAclModule)表控制层
 *
 * @author makejava
 * @since 2023-03-22 18:09:38
 */
@RestController
@RequestMapping("sys/sysAclModule")
public class SysAclModuleController {
    /**
     * 服务对象
     */
    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysTreeService sysTreeService;

    @GetMapping("/tree")
    public ResultBody aclModuleTree() {
        return ResultBody.success(sysAclModuleService.aclModuleTree());
    }

    @PostMapping("/add")
    public ResultBody add(SysAclModuleParam param) {
        return ResultBody.success(sysAclModuleService.insert(param));
    }


    @PutMapping("/update")
    public ResultBody edit(SysAclModuleParam param) {
        return ResultBody.success(sysAclModuleService.update(param));
    }

    @DeleteMapping
    public ResultBody deleteById(Integer id) {
        return ResultBody.success(sysAclModuleService.deleteById(id));
    }

    @GetMapping("{id}")
    public ResultBody queryById(@PathVariable("id") Integer id) {
        return ResultBody.success(sysAclModuleService.queryById(id));
    }

    @RequestMapping("/roleTree")
    @ResponseBody
    public ResultBody roleTree(@RequestParam("roleId") int roleId) {
        return ResultBody.success(sysTreeService.roleTree(roleId));
    }

}

