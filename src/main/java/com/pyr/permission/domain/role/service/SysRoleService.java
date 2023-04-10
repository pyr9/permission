package com.pyr.permission.domain.role.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.base.model.BaseEntity;
import com.pyr.permission.domain.role.mapper.SysRoleAclMapper;
import com.pyr.permission.domain.role.mapper.SysRoleMapper;
import com.pyr.permission.domain.role.mapper.SysRoleUserMapper;
import com.pyr.permission.domain.role.model.SysRole;
import com.pyr.permission.domain.role.param.SysRoleParam;
import com.pyr.permission.domain.user.mapper.SysUserMapper;
import com.pyr.permission.domain.user.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (SysRole)表服务实现类
 *
 * @author makejava
 * @since 2023-03-22 18:09:39
 */
@Service
public class SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleAclMapper sysRoleAclMapper;

    public SysRole insert(SysRoleParam param) {
        BeanValidator.check(param);
        SysRole SysRole = (SysRole) SysBeanUtil.convert(param, SysRole.class);
        sysRoleMapper.insert(SysRole);
        return SysRole;
    }

    public boolean update(SysRoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectById(param.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        SysRole after = (SysRole) SysBeanUtil.convert(param, SysRole.class);
        return sysRoleMapper.updateById(after) > 0;
    }

    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    public boolean deleteById(Integer id) {
        SysRole aclModule = sysRoleMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(aclModule, "待删除的角色不存在，无法删除");
        return this.sysRoleMapper.deleteById(id) > 0;
    }

    private boolean checkExist(String name) {
        return sysRoleMapper.countByName(name) > 0;
    }

    public List<SysRole> getRoleListByUserId(long userId) {
        List<Long> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    public List<SysRole> getRoleListByAclId(long aclId) {
        List<Long> roleIds = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIds);
    }

    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Long> roleIdList = roleList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<Long> userIdList = sysRoleUserMapper.getUserIdsByRoleIds(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }
}
