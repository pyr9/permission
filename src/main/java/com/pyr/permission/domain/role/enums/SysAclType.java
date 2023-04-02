package com.pyr.permission.domain.role.enums;

public enum SysAclType {
    MENU(0, "菜单"),
    BUTTON(1, "按钮");

    private final Integer code;
    private final String msg;

    SysAclType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
