package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;

@Data
public class SysRole extends BaseEntity {
    private String name;

    private Integer type;

    private Integer status;
}