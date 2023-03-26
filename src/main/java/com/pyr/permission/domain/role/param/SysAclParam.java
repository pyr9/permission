package com.pyr.permission.domain.role.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SysAclParam {
    private Integer id;

    private String code;

    private String name;

    @NotBlank(message = "权限模块不可以为空")
    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;
}
