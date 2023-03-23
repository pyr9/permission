package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;

@Data
public class SysRoleAcl extends BaseEntity {
    private Integer roleId;

    private Integer aclId;
}