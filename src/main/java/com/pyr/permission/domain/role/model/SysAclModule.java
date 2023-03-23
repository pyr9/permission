package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;

@Data
public class SysAclModule extends BaseEntity {
    private String name;

    private Integer parentId;

    private String level;

    private Integer status;

    private Integer seq;
}