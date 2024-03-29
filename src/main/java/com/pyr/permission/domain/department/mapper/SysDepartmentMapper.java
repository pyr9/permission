package com.pyr.permission.domain.department.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyr.permission.domain.department.model.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {
    int countByNameAndParentId(@Param("parentId") Long parentId, @Param("name") String name, @Param("id") Integer id);

    List<SysDepartment> getAllDept();

    int deleteByPrimaryKey(Integer id);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

    List<SysDepartment> getChildDeptListByLevel(String s);

    void batchUpdateLevel(@Param("sysDeptList") List<SysDepartment> sysDeptList);

    int countByParentId(@Param("parentId") Long parentId);
}