package com.pyr.permission.domain.role.mapper;

import com.pyr.permission.domain.role.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Long> getAclIdListByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    void deleteByRoleId(@Param("roleId") long roleId);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Long> getRoleIdListByAclId(@Param("aclId") long aclId);
}