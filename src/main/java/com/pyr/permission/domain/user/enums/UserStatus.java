package com.pyr.permission.domain.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private final Integer code;
    private final String msg;

    UserStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}