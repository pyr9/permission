package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.domain.role.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleAclMapper extends BaseMapper<SysRoleAcl> {

    List<Long> getAclIdListByRoleIdList(@Param("roleIdList") List<Long> roleIdList);

    void deleteByRoleId(@Param("roleId") long roleId);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<Long> getRoleIdListByAclId(@Param("aclId") long aclId);
}