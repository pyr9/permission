package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;

@Data
public class SysAcl extends BaseEntity {
    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;
}