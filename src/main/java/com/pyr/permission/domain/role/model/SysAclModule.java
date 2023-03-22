package com.pyr.permission.domain.role.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysAclModule {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer status;

    private Integer seq;

    private String remark;

    private String creator;

    private Date createTime;

    private String creatorIp;
}