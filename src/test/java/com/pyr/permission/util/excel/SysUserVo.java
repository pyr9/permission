package com.pyr.permission.util.excel;

import com.pyr.permission.common.excel.I18nText;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SysUserVo {
    @I18nText("用户名")
    private String userName;

    @I18nText("电话号码")
    private String telephone;

    @I18nText("邮箱")
    private String mail;

    private String password;

    private Integer departmentId;

    private Integer status;
}
