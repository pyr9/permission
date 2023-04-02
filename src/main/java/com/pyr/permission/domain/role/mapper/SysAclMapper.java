package com.pyr.permission.domain.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.domain.role.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper extends BaseMapper<SysAcl> {
    int countByAclModuleId(@Param("aclModuleId") Long aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId") Long aclModuleId, @Param("page") PageQuery page);

    int countByNameAndAclModuleId(@Param("aclModuleId") Long aclModuleId, @Param("name") String name, @Param("id") Long id);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Long> idList);

    List<SysAcl> getByUrl(String url);
}