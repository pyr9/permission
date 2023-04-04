package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.domain.role.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    int insert(SysRoleUser record);

    List<Long> getUserIdListByRoleId(Long roleId);

    void deleteByRoleId(@Param("roleId") long roleId);

    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

    List<Long> getRoleIdListByUserId(@Param("userId") long userId);
}