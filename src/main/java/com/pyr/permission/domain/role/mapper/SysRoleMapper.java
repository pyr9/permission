package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.domain.role.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    SysRole selectByPrimaryKey(Integer id);

    default List<SysRole> getAll() {
        return selectList(new QueryWrapper<>());
    }

    int countByName(String name);

    List<SysRole> getByIdList(@Param("idList") List<Long> idList);
}