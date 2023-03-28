package com.pyr.permission.interfaces;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.base.model.BaseEntity;
import com.pyr.permission.domain.role.service.SysRoleUserService;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role/user")
public class SysRoleUserController {
    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/roleUserTree")
    @ResponseBody
    public ResultBody users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Long> selectedUserIdSet = selectedUserList.stream().map(BaseEntity::getId).collect(Collectors.toSet());
        for (SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return ResultBody.success(map);
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public ResultBody changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false) List<Integer> userIds) {
        sysRoleUserService.changeRoleUsers(roleId, userIds);
        return ResultBody.success();
    }
}
