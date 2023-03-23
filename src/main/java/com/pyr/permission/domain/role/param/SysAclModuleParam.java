package com.pyr.permission.domain.role.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SysAclModuleParam {
    private Integer id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(max = 15, min = 2, message = "权限模块名称长度需要在2-15个字之间")

    private String name;


    private Integer parentId = 0;

    @NotNull(message = "状态")
    private Integer status;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注的长度需要在150个字以内")
    private String remark;

}
