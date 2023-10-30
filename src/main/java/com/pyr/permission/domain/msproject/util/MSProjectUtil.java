package com.pyr.permission.domain.msproject.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.pyr.permission.domain.msproject.model.MSProject;
import com.pyr.permission.domain.msproject.model.MSTaskPredecessor;
import com.pyr.permission.domain.msproject.model.PrdPlanMissionDependencyEnum;
import lombok.extern.slf4j.Slf4j;
import net.sf.mpxj.*;
import net.sf.mpxj.mspdi.MSPDIWriter;
import net.sf.mpxj.reader.UniversalProjectReader;
import net.sf.mpxj.writer.ProjectWriter;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.pyr.permission.domain.msproject.model.MSProjectPropertyEnum.*;


@Slf4j
public class MSProjectUtil {
    public static final int ROOT_LEVEL_NUM = 1;
    public static final int TEXT10_INDEX = 10;
    public static final int TEXT1_INDEX = 1;
    public static final int NOT_SAVE = 0;
    public static final String DELIMITER = "|";
    public static final String TASK_PREDECESSOR_REGEX = "(\\d+)\\s+(" + getTaskDependencyStr() + ")([+-]\\d+)?d?";

    public static File writeMppFileToDB(List<MSProject> projects, String templateFilePath) {
        long time = System.currentTimeMillis();
        String xmlPath = "D:\\tmp\\test\\" + time + ".xml";
//        String xmlPath = TempFileUtil.getRandomFilePath(".xml");
        generateTempXmlFile(projects, templateFilePath, xmlPath);

        String mppPath = "D:\\tmp\\test\\项目计划1_" + time + ".mpp";
//        String mppPath = TempFileUtil.getRandomFilePath(".mpp");
        convertXmlToMpp(xmlPath, mppPath, templateFilePath);
//        TempFileUtil.asyncDel(new File(xmlPath));
        return new File(mppPath);
    }

    public static MSTaskPredecessor covertToMSTaskPredecessor(String predecessorStr) {
        Matcher matcher = Pattern.compile(TASK_PREDECESSOR_REGEX).matcher(predecessorStr);
        if (matcher.find()) {
            int taskId = Integer.parseInt(matcher.group(1));
            int lagDays = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
            int relationTypeCode = PrdPlanMissionDependencyEnum.getEnum(matcher.group(2)).getCode();
            return MSTaskPredecessor.builder()
                    .taskId(taskId)
                    .relationType(relationTypeCode)
                    .lagDays(lagDays)
                    .build();
        }
        return null;
    }

    private static String getTaskDependencyStr() {
        return Arrays.stream(PrdPlanMissionDependencyEnum.values())
                .map(PrdPlanMissionDependencyEnum::getMessage)
                .collect(Collectors.joining(DELIMITER));
    }

    /**
     * 生成临时xml文件，因为没有办法直接输出project文件，所以需要xml文件转换下
     */
    private static void generateTempXmlFile(List<MSProject> projects, String templateFilePath, String xmlPath) {
        try {
            UniversalProjectReader universalProjectReader = new UniversalProjectReader();
            ProjectFile pf = universalProjectReader.read(new File(templateFilePath));
            writeChildrenTask(projects, ROOT_LEVEL_NUM, pf.addTask(), 0L);
            setTaskPredecessors(pf, projects);
            ProjectWriter writer = new MSPDIWriter();
            writer.write(pf, xmlPath);
        } catch (IOException | MPXJException ioe) {
            log.error("XML file created fail." + ioe.getMessage());
        }
    }

    /**
     * 将xml文件转换成Mpp文件
     */
    //	导出mpp文件
    public static void convertXmlToMpp(String xmlPath, String mppPath, String templateFilePath) {
        ActiveXComponent activexComponent = new ActiveXComponent(APPLICATION.getName());
        //关闭静默打开
        activexComponent.setProperty(VISIBLE.name(), false);
        //设置关闭弹窗
        activexComponent.setProperty(DISPLAY_ALERTS.getName(), false);
        Dispatch projects = activexComponent.getProperty(PROJECTS.getName()).toDispatch();
        Dispatch newProject = Dispatch.call(projects, ADD.getName(), false, templateFilePath).toDispatch();
        copyTasksFormXmlProject(xmlPath, activexComponent, newProject);
        saveMppFile(mppPath, newProject, projects, activexComponent);
    }

    private static void saveMppFile(String mppPath, Dispatch newProject, Dispatch projects, ActiveXComponent activexComponent) {
        try {
            Dispatch.call(newProject, SAVE_AS.getName(), mppPath);
            Dispatch.call(activexComponent, FILE_CLOSE.getName(), NOT_SAVE);
            log.info("MPP file created successfully.");
        } catch (Exception e) {
            log.info("MPP file created fail." + e.getMessage());
        } finally {
            // 获取当前打开的项目数量
            int openProjectsCount = Dispatch.get(projects, COUNT.getName()).getInt();
            if (openProjectsCount == 0) {
                activexComponent.invoke(QUIT.getName());
            }
        }
    }

    /**
     * 将xmlProject的tasks复制到mppProject的task里
     */
    private static void copyTasksFormXmlProject(String xmlPath, ActiveXComponent activexComponent, Dispatch newProject) {
        // 打开xml文件, JACOB 用于与 Microsoft Project 应用程序进行交互，因此需要打开该应用程序来执行操作
        Variant result = Dispatch.call(activexComponent, FILE_OPEN.getName(), xmlPath);
        Dispatch newTasks = Dispatch.get(newProject, TASKS.getName()).toDispatch();
        if (result.getBoolean()) {
            Dispatch xmlProject = Dispatch.get(activexComponent, ACTIVE_PROJECT.getName()).toDispatch();
            // 获取xmlProject的所有task
            Dispatch tasks = Dispatch.get(xmlProject, TASKS.getName()).toDispatch();
            int taskCount = Dispatch.call(tasks, COUNT.getName()).getInt();
            for (int i = 1; i <= taskCount; i++) {
                Dispatch item = Dispatch.call(tasks, ITEM.getName(), i).getDispatch();
                Dispatch currentTask = Dispatch.call(newTasks, ADD.getName(), Dispatch.get(item, NAME.getName())).toDispatch();
                Dispatch.put(currentTask, MANUAL.getName(), Dispatch.get(item, MANUAL.getName()));
                Dispatch.put(currentTask, TEXT1.getName(), Dispatch.get(item, TEXT1.getName()));
                Dispatch.put(currentTask, TEXT10.getName(), Dispatch.get(item, TEXT10.getName()));
                Dispatch.put(currentTask, PERCENT_COMPLETE.getName(), Dispatch.get(item, PERCENT_COMPLETE.getName()));
                Dispatch.put(currentTask, BASELINE_START.getName(), Dispatch.get(item, BASELINE_START.getName()));
                Dispatch.put(currentTask, BASELINE_FINISH.getName(), Dispatch.get(item, BASELINE_FINISH.getName()));
                Dispatch.put(currentTask, START.getName(), Dispatch.get(item, START.getName()));
                Dispatch.put(currentTask, FINISH.getName(), Dispatch.get(item, FINISH.getName()));
                Dispatch.put(currentTask, OUTLINE_LEVEL.getName(), Dispatch.get(item, OUTLINE_LEVEL.getName()));
                Dispatch.put(currentTask, DURATION.getName(), Dispatch.get(item, DURATION.getName()));
                putPredecessorTasks(newTasks, item, currentTask);
            }
        }
        Dispatch.call(activexComponent, FILE_CLOSE.getName(), NOT_SAVE);
    }

    private static void putPredecessorTasks(Dispatch newTasks, Dispatch item, Dispatch currentTask) {
        Dispatch predecessorTasks = Dispatch.get(item, TASK_DEPENDENCIES.getName()).toDispatch();
        int taskSize = Dispatch.get(predecessorTasks, COUNT.getName()).getInt();
        for (int j = 1; j <= taskSize; j++) {
            Dispatch predecessorTask = Dispatch.call(predecessorTasks, ITEM.getName(), j).getDispatch();
            Dispatch predecessorTaskFrom = Dispatch.call(predecessorTask, FROM.getName()).getDispatch();
            int predecessorTaskId = Dispatch.get(predecessorTaskFrom, ID.getName()).getInt();
            int lag = Dispatch.get(predecessorTask, LAG.getName()).getInt();
            int type = Dispatch.get(predecessorTask, TYPE.getName()).getInt();
            Dispatch byId = findById(newTasks, predecessorTaskId);
            if (byId != null) {
                Dispatch.call(currentTask, LINK_PREDECESSORS.getName(), byId, type, new Variant(String.valueOf(lag / 480)));
            }
        }
    }

    private static Dispatch findById(Dispatch tasks, int taskId) {
        int taskCount = Dispatch.call(tasks, COUNT.getName()).getInt();
        for (int i = 1; i < taskCount; i++) {
            Dispatch item = Dispatch.call(tasks, ITEM.getName(), i).getDispatch();
            int id = Dispatch.get(item, ID.getName()).getInt();
            if (taskId == id) {
                return item;
            }
        }
        return null;
    }

    private static Task createTask(MSProject msProject, Task parentTask, int currentLevelNum) {
        Task task;
        if (parentTask.getID() == null) {
            task = parentTask;
        } else {
            task = parentTask.addTask();
        }
        task.setUniqueID(msProject.getId().intValue());
        task.setText(TEXT10_INDEX, msProject.getText10().replaceAll("\t", ""));
        task.setName(msProject.getTaskName());
        task.setText(TEXT1_INDEX, msProject.getText1().replaceAll("\t", ""));
        task.setPercentageComplete(msProject.getPercentComplete());
        task.setDuration(Duration.getInstance(msProject.getDuration(), TimeUnit.DAYS));
        task.setStart(msProject.getStartDate());
        task.setFinish(msProject.getEndDate());
        task.setBaselineStart(msProject.getBaselineStart());
        task.setBaselineFinish(msProject.getBaselineFinish());
        //如果是读取第一层
        if (currentLevelNum == 1) {
            task.setOutlineLevel(1);
            task.setID(1);
        } else {
            task.setOutlineLevel(parentTask.getOutlineLevel() + 1);
            task.setID(parentTask.getID() + 1);
        }
        return task;
    }

    private static void writeChildrenTask(List<MSProject> msProjects, int levelNum, Task parentTask, Long parentId) {
        //首先从第一层开始读取
        List<MSProject> subMSProjects = getSubProjects(msProjects, parentId, levelNum);
        for (MSProject msProject : subMSProjects) {
            int currentLevelNum = levelNum;
            List<MSProject> childrenList = getSubProjects(msProjects, msProject.getId(), currentLevelNum + 1);
            //当前层级为父任务，进行父任务的写入，然后进行下一次递归
            if (childrenList.size() > 0) {
                Task task = createTask(msProject, parentTask, currentLevelNum);
                currentLevelNum++;
                writeChildrenTask(msProjects, currentLevelNum, task, msProject.getId());
            } else {
                //当前层级为最底层
                createTask(msProject, parentTask, currentLevelNum);
            }
        }
    }

    /**
     * 设置前置依赖
     */
    private static void setTaskPredecessors(ProjectFile projectFile, List<MSProject> subMSProjects) {
        List<MSProject> msProjectsWithPredecessors = subMSProjects.stream()
                .filter(project -> CollectionUtils.isNotEmpty(project.getMsTaskPredecessors()))
                .collect(Collectors.toList());

        for (MSProject project : msProjectsWithPredecessors) {
            Task taskWithPredecessors = projectFile.getTasks().getByUniqueID(project.getId().intValue());
            List<MSTaskPredecessor> msTaskPredecessors = project.getMsTaskPredecessors();
            if (taskWithPredecessors != null && CollectionUtils.isNotEmpty(msTaskPredecessors)) {
                msTaskPredecessors.forEach(msTaskPredecessor -> {
                    Task predecessorTask = projectFile.getTasks().getByUniqueID(msTaskPredecessor.getTaskId());
                    taskWithPredecessors.addPredecessor(predecessorTask,
                            RelationType.getInstance(msTaskPredecessor.getRelationType()),
                            Duration.getInstance(msTaskPredecessor.getLagDays(), TimeUnit.DAYS));
                });
            }
        }
    }

    /**
     * 获取子任务列表
     */
    private static List<MSProject> getSubProjects(List<MSProject> projects, Long parentId, Integer levelNum) {
        List<MSProject> subList = projects.stream()
                .filter(pro -> Objects.equals(pro.getLevel(), levelNum))
                .collect(Collectors.toList());
        if (parentId.equals(0L)) {
            return subList;
        }
        // 筛选当前项目的子项目
        return subList.stream()
                .filter(pro -> Objects.equals(parentId, pro.getParentId()))
                .collect(Collectors.toList());
    }
}
