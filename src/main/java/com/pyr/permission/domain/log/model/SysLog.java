package com.pyr.permission.domain.log.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysLog extends BaseEntity {
    private Integer type;

    private Integer targetId;

    private String oldValue;

    private String newValue;

    private Integer status;
}