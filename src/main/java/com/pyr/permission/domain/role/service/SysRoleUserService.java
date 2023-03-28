package com.pyr.permission.domain.role.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pyr.permission.domain.role.mapper.SysRoleUserMapper;
import com.pyr.permission.domain.role.model.SysRoleUser;
import com.pyr.permission.domain.user.mapper.SysUserMapper;
import com.pyr.permission.domain.user.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    private static boolean userIdsNotChange(List<Integer> userIds, List<Integer> originUserIdList) {
        if (originUserIdList.size() == userIds.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIds);
            originUserIdSet.removeAll(userIdSet);
            return CollectionUtils.isEmpty(originUserIdSet);
        }
        return false;
    }

    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    public void changeRoleUsers(int roleId, List<Integer> userIds) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (userIdsNotChange(userIds, originUserIdList)) return;
        updateRoleUsers(roleId, userIds);
    }

    private void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }
}
