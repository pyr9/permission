package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;

@Data
public class SysRoleUser extends BaseEntity {
    private Integer userId;

    private Integer roleId;
}