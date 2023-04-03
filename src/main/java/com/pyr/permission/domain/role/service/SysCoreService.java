package com.pyr.permission.domain.role.service;

import com.google.common.collect.Lists;
import com.pyr.permission.domain.role.mapper.SysAclMapper;
import com.pyr.permission.domain.role.mapper.SysRoleAclMapper;
import com.pyr.permission.domain.role.model.SysAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public List<SysAcl> getRoleAclList(long roleId) {
        List<Long> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.<Long>newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }
}
