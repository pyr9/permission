package com.pyr.permission.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleUser {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;
}