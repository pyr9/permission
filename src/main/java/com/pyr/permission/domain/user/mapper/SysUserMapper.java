package com.pyr.permission.domain.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.domain.user.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    SysUser findByKeyword(@Param("keyword") String keyword);


    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByDepartmentId(@Param("deptId") Long deptId);

    List<SysUser> pageByDepartmentId(@Param("deptId") Long deptId, @Param("page") PageQuery pageQuery);

    List<SysUser> getAll();

    List<SysUser> getByIdList(List<Integer> userIdList);
}