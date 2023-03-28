package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysAcl extends BaseEntity {
    private String code;

    private String name;

    private Long aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;
}