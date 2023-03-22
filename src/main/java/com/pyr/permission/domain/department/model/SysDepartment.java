package com.pyr.permission.domain.department.model;

import lombok.*;

import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SysDepartment {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private Integer creatorId;

    private Date createTime;

    private String creatorIp;

}