package com.pyr.permission.domain.msproject.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MSProject {
    private Long Id;
    // 上级Id
    private Long parentId;
    // 结构层级
    private Integer level;
    // 任务名称
    private String taskName;
    // 工期
    private Integer duration;
    // 开始时间
    private Date startDate;
    // 结束时间
    private Date endDate;
    // 基准开始时间
    private Date BaselineStart;
    // 基准结束时间
    private Date BaselineFinish;
    // 任务完成百分比
    private Integer PercentComplete;
    // 任务是否是手动执行任务
    private boolean Manual;
    // 文本1
    private String Text1;
    // 文本10
    private String Text10;
    // 前置依赖
    private List<MSTaskPredecessor> msTaskPredecessors;
}
