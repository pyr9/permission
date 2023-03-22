package com.pyr.permission.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;
}