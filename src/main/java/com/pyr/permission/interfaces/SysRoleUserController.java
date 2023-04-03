package com.pyr.permission.interfaces;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.base.model.BaseEntity;
import com.pyr.permission.domain.role.dto.BaseUpdateDto;
import com.pyr.permission.domain.role.service.SysRoleUserService;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色与用户
 */
@Controller
@RequestMapping("/sys/roleUser")
public class SysRoleUserController {
    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysUserService sysUserService;

    /**
     * 查询某个角色下的用户列表（以部门组织架构作树形结构展示）
     */
    @RequestMapping("/tree")
    @ResponseBody
    public ResultBody tree(@RequestParam("roleId") long roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Long> selectedUserIdSet = selectedUserList.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        for (SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        Map<String, List<SysUser>> map = Maps.newHashMap();
        // TODO: 2023/4/3 修改成树结构 
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return ResultBody.success(map);
    }

    /**
     * 给角色分配用户
     */
    @RequestMapping("/update")
    @ResponseBody
    public ResultBody update(@RequestBody BaseUpdateDto baseUpdateDto) {
        long roleId = baseUpdateDto.getEntityId();
        List<Long> userIds = baseUpdateDto.getRelationEntityIds();
        sysRoleUserService.update(roleId, userIds);
        return ResultBody.success();
    }
}
