package com.pyr.permission.domain.role.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.pyr.permission.common.util.SysIdWorker;
import com.pyr.permission.domain.role.mapper.SysRoleAclMapper;
import com.pyr.permission.domain.role.model.SysRoleAcl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleAclService {
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public void update(Long roleId, List<Long> aclIdList) {
        List<Long> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (originAclIdList.size() == aclIdList.size()) {
            Set<Long> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Long> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }
        updateRoleAcls(roleId, aclIdList);
    }

    @Transactional
    public void updateRoleAcls(Long roleId, List<Long> aclIdList) {
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Long aclId : aclIdList) {
            SysRoleAcl roleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).build();
            roleAcl.setId(SysIdWorker.generateId());
            roleAclList.add(roleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }

}
