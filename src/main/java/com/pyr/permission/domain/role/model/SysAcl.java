package com.pyr.permission.domain.role.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysAcl {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;
}