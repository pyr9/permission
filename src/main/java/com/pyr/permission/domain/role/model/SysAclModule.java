package com.pyr.permission.domain.role.model;

import com.pyr.permission.domain.base.model.BaseTreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysAclModule extends BaseTreeEntity {
    private Integer status;
}