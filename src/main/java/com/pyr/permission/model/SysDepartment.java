package com.pyr.permission.model;

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

    private String creator;

    private Date createTime;

    private String creatorip;


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public void setCreatorip(String creatorip) {
        this.creatorip = creatorip == null ? null : creatorip.trim();
    }
}