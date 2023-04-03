package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.dto.BaseUpdateDto;
import com.pyr.permission.domain.role.service.SysRoleAclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色管理： 维护角色与权限的关系
 */
@Controller
@RequestMapping("/sys/roleAcl")
public class SysRoleAclController {
    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    /**
     * 获取当前角色下的权限点结构
     */
    @RequestMapping("/tree")
    @ResponseBody
    public ResultBody roleTree(@RequestParam("roleId") Long roleId) {
        return ResultBody.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 更新改角色下，权限点的集合
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultBody update(@RequestBody BaseUpdateDto baseUpdateDto) {
        long roleId = baseUpdateDto.getEntityId();
        List<Long> aclIds = baseUpdateDto.getRelationEntityIds();
        sysRoleAclService.update(roleId, aclIds);
        return ResultBody.success();
    }
}