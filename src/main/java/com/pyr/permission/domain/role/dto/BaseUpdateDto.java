package com.pyr.permission.domain.role.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BaseUpdateDto {
    @NotNull(message = "用户id不能为空")
    long entityId;
    List<Long> relationEntityIds;
}
