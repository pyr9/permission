package com.pyr.permission.domain.msproject.model;

public enum MSProjectPropertyEnum {
    APPLICATION("MSProject.Application"),
    VISIBLE("Visible"),
    DISPLAY_ALERTS("DisplayAlerts"),
    PROJECTS("Projects"),
    TASKS("Tasks"),
    NAME("Name"),
    MANUAL("Manual"),
    TEXT1("Text1"),
    TEXT10("Text10"),
    PERCENT_COMPLETE("PercentComplete"),
    BASELINE_START("BaselineStart"),
    BASELINE_FINISH("BaselineFinish"),
    START("Start"),
    FINISH("Finish"),
    OUTLINE_LEVEL("outlineLevel"),
    TASK_DEPENDENCIES("TaskDependencies"),
    FROM("From"),
    ID("Id"),
    LAG("Lag"),
    TYPE("Type"),
    LINK_PREDECESSORS("LinkPredecessors"),
    DURATION("Duration"),

    //method
    ADD("Add"),
    FILE_OPEN("FileOpen"),
    FILE_CLOSE("FileClose"),
    ACTIVE_PROJECT("ActiveProject"),
    COUNT("Count"),
    ITEM("Item"),
    SAVE_AS("SaveAs"),
    QUIT("Quit");
    private final String name;

    MSProjectPropertyEnum(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        System.out.println(MSProjectPropertyEnum.VISIBLE.getName());
    }

    public String getName() {
        return name;
    }
}
