package com.pyr.permission.domain.role.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SysAclParam {
    private Long id;

    @NotNull(message = "权限模块code不可以为空")
    private String code;


    @NotBlank(message = "权限点名称不可以为空")
    @Length(min = 2, max = 20, message = "权限点名称长度需要在2-20个字之间")
    private String name;

    @NotNull(message = "必须指定权限模块")
    private Long aclModuleId;

    @NotNull(message = "权限模块url不可以为空")
    private String url;


    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1, message = "权限点类型不合法")
    @Max(value = 3, message = "权限点类型不合法")
    private Integer type;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;
}
