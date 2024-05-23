package com.pyr.permission.domain.msproject.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class MSTaskPredecessor {
    private Integer taskId;
    private Integer relationType;
    private Integer lagDays;
}
