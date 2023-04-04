package com.pyr.permission.domain.role.service;

import com.google.common.collect.Lists;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.domain.base.model.BaseEntity;
import com.pyr.permission.domain.role.mapper.SysAclMapper;
import com.pyr.permission.domain.role.mapper.SysRoleAclMapper;
import com.pyr.permission.domain.role.mapper.SysRoleUserMapper;
import com.pyr.permission.domain.role.model.SysAcl;
import com.pyr.permission.domain.user.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    public List<SysAcl> getRoleAclList(long roleId) {
        List<Long> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Long>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
//        <!-- url is not null and url != '' and  #{url} REGEXP url-->
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }
        List<SysAcl> userAclList = getCurrentUserAclList();
        Set<Long> userAclIdSet = userAclList.stream().map(BaseEntity::getId).collect(Collectors.toSet());

        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            // 权限点禁用
            if (acl == null || acl.getStatus() != 1) { // 权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        return !hasValidAcl;
    }

    public List<SysAcl> getCurrentUserAclList() {
        Long userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    public List<SysAcl> getUserAclList(long userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Long> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Long> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public List<SysAcl> getUserAcls(long userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Long> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Long> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        return sysUser.getMail().contains("admin");
    }
}
