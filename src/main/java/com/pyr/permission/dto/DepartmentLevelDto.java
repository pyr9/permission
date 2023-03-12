package com.pyr.permission.dto;

import com.google.common.collect.Lists;
import com.pyr.permission.model.SysDepartment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class DepartmentLevelDto extends SysDepartment {

    private List<DepartmentLevelDto> subDepartments = Lists.newArrayList();

    public static DepartmentLevelDto adapt(SysDepartment department) {
        DepartmentLevelDto dto = new DepartmentLevelDto();
        BeanUtils.copyProperties(department, dto);
        return dto;
    }
}
