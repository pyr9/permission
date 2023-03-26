package com.pyr.permission.domain.role.enums;

public enum SysRoleStatus {
    ADMIN(0, "管理员角色"),
    OTHER(1, "其他");

    private final Integer code;
    private final String msg;

    SysRoleStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
