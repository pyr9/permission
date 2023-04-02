package com.pyr.permission.domain.base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseTreeEntity extends BaseEntity {
    private String name;

    private Long parentId;

    private String level;

    private Integer seq;
}
