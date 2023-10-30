package com.pyr.permission.domain.msproject.model;

public enum PrdPlanMissionDependencyEnum {
    FINISH_START(1, "FS"),
    START_START(2, "SS"),
    FINISH_FINISH(3, "FF"),
    START_FINISH(4, "SF");
    private final Integer code;
    private final String msg;

    PrdPlanMissionDependencyEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static PrdPlanMissionDependencyEnum getEnum(String msg) {
        for (PrdPlanMissionDependencyEnum v : values()) {
            if (v.getMessage().compareTo(msg) == 0) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public static PrdPlanMissionDependencyEnum getEnum(Integer code) {
        for (PrdPlanMissionDependencyEnum v : values()) {
            if (v.getCode().equals(code)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.msg;
    }

}
