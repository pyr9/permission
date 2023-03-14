package com.pyr.permission.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysUser {
    private Integer id;

    private String userName;

    private String telephone;

    private String mail;

    private String passward;

    private Integer departmentId;

    private Integer status;

    private String remark;

    private String creator;

    private Date createTime;

    private String creatorip;

    public static SysUser of(String userName, String telephone, String mail, String passward, Integer departmentId, Integer status, String remark) {
        return SysUser.builder()
                .userName(userName)
                .telephone(telephone)
                .mail(mail)
                .passward(passward)
                .departmentId(departmentId)
                .status(status)
                .remark(remark)
                .build();
    }
}