package com.pyr.permission.domain.department.model;

import com.pyr.permission.domain.base.model.BaseEntity;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SysDepartment extends BaseEntity {

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

}