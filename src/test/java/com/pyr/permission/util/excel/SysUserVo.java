package com.pyr.permission.util.excel;

import com.pyr.permission.common.excel.I18nText;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserVo {
    @I18nText(value = "用户名", customVuex = {"sith", "vendor"})
    private String userName;

    @I18nText(value = "电话号码", customVuex = {"sith"})
    private String telephone;

    @I18nText(value = "邮箱", customVuex = {"sith", "vendor"})
    private String mail;

    private String password;

    private Integer departmentId;

    private Integer status;
}
