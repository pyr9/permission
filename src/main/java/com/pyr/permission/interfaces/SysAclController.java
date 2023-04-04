package com.pyr.permission.interfaces;

import com.google.common.collect.Maps;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.domain.role.model.SysRole;
import com.pyr.permission.domain.role.param.SysAclParam;
import com.pyr.permission.domain.role.service.SysAclService;
import com.pyr.permission.domain.role.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (SysAclModule)表控制层
 *
 * @author makejava
 * @since 2023-03-22 18:09:38
 */
@RestController
@RequestMapping("sys/acl")
public class SysAclController {
    /**
     * 服务对象
     */
    @Resource
    private SysAclService sysAclService;

    @Resource
    private SysRoleService sysRoleService;

    @PostMapping("/add")
    public ResultBody add(SysAclParam param) {
        return ResultBody.success(sysAclService.insert(param));
    }


    @PutMapping("/update")
    public ResultBody edit(SysAclParam param) {
        return ResultBody.success(sysAclService.update(param));
    }

    @DeleteMapping
    public ResultBody deleteById(Long id) {
        return ResultBody.success(sysAclService.deleteById(id));
    }

    @RequestMapping("/page")
    public ResultBody queryByAclModuleId(@RequestParam("aclModuleId") Long aclModuleId, PageQuery pageQuery) {
        return ResultBody.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    /**
     * 查询某个权限点下的角色列表+用户列表
     */
    @RequestMapping("/rolesAndUsers")
    @ResponseBody
    public ResultBody rolesAndUsers(@RequestParam("aclId") long aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return ResultBody.success(map);
    }
}

