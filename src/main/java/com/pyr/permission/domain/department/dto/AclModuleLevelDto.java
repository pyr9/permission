package com.pyr.permission.domain.department.dto;

import com.google.common.collect.Lists;
import com.pyr.permission.domain.role.model.SysAclModule;
import com.pyr.permission.domain.role.vo.AclVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclVo> aclList = Lists.newArrayList();


    public static AclModuleLevelDto adapt(SysAclModule aclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(aclModule, dto);
        return dto;
    }
}
