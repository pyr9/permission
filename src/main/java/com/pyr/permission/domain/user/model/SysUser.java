package com.pyr.permission.domain.user.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.*;

@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysUser extends BaseEntity {

    private String userName;

    private String telephone;

    private String mail;

    private String password;

    private Integer departmentId;

    private Integer status;

    public static SysUser of(String userName, String telephone, String mail, String password, Integer departmentId, Integer status) {
        return SysUser.builder()
                .userName(userName)
                .telephone(telephone)
                .mail(mail)
                .password(password)
                .departmentId(departmentId)
                .status(status)
                .build();
    }
}