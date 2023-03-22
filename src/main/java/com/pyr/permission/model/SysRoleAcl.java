package com.pyr.permission.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;
}