package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.domain.role.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole selectByPrimaryKey(Integer id);

    List<SysRole> getAll();

    int countByName(String name);
}