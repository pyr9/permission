package com.pyr.permission.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private String oldValue;

    private String newValue;

    private Integer status;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;
}