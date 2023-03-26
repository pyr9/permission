package com.pyr.permission.domain.role.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.role.mapper.SysRoleMapper;
import com.pyr.permission.domain.role.model.SysRole;
import com.pyr.permission.domain.role.param.SysRoleParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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


    public SysRole insert(SysRoleParam param) {
        BeanValidator.check(param);
        SysRole SysRole = (SysRole) SysBeanUtil.convert(param, SysRole.class);
        sysRoleMapper.insertSelective(SysRole);
        return SysRole;
    }

    public boolean update(SysRoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName())) {
            throw new ParamException("角色名称已经存在");
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");
        SysRole after = (SysRole) SysBeanUtil.convert(param, SysRole.class);
        return sysRoleMapper.updateByPrimaryKeySelective(after) > 0;
    }

    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    public boolean deleteById(Integer id) {
        SysRole aclModule = sysRoleMapper.selectByPrimaryKey(id);
        Preconditions.checkNotNull(aclModule, "待删除的角色不存在，无法删除");
        return this.sysRoleMapper.deleteByPrimaryKey(id) > 0;
    }

    private boolean checkExist(String name) {
        return sysRoleMapper.countByName(name) > 0;
    }
}
