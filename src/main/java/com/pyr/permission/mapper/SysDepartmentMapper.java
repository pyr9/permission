package com.pyr.permission.mapper;

import com.pyr.permission.model.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDepartmentMapper {
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    List<SysDepartment> getAllDept();

    int deleteByPrimaryKey(Integer id);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

    List<SysDepartment> getChildDeptListByLevel(String s);

    void batchUpdateLevel(List<SysDepartment> deptList);
}