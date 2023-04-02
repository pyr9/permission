package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.domain.role.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper extends BaseMapper<SysAcl> {

    int insert(SysAcl record);

    int countByAclModuleId(@Param("aclModuleId") int aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("page") PageQuery page);

    int countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId, @Param("name") String name, @Param("id") Integer id);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Long> idList);

    List<SysAcl> getByUrl(String url);
}