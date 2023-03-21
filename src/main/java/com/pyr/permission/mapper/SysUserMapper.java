package com.pyr.permission.mapper;

import com.pyr.permission.model.SysUser;
import com.pyr.permission.page.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    SysUser findByKeyword(@Param("keyword") String keyword);


    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByDepartmentId(@Param("deptId") int deptId);

    List<SysUser> pageByDepartmentId(@Param("deptId") int deptId, @Param("page") PageQuery pageQuery);
}