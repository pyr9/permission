package com.pyr.permission.domain.role.vo;

import com.pyr.permission.domain.role.model.SysAcl;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class AclVo extends SysAcl {

    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean disable = false;
}