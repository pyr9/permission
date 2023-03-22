package com.pyr.permission.domain.department.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.pyr.permission.common.util.LevelUtil;
import com.pyr.permission.domain.department.dto.DepartmentLevelDto;
import com.pyr.permission.domain.department.mapper.SysDepartmentMapper;
import com.pyr.permission.domain.department.model.SysDepartment;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    public List<DepartmentLevelDto> deptTree() {
        List<SysDepartment> deptList = sysDepartmentMapper.getAllDept();

        List<DepartmentLevelDto> dtoList = Lists.newArrayList();
        for (SysDepartment dept : deptList) {
            DepartmentLevelDto dto = DepartmentLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    public List<DepartmentLevelDto> deptListToTree(List<DepartmentLevelDto> deptLevelList) {
        if (CollectionUtils.isEmpty(deptLevelList)) {
            return Lists.newArrayList();
        }
        // level -> [dept1, dept2, ...] Map<String, List<Object>>
        Multimap<String, DepartmentLevelDto> levelDeptMap = ArrayListMultimap.create();
        List<DepartmentLevelDto> rootList = Lists.newArrayList();

        for (DepartmentLevelDto dto : deptLevelList) {
            levelDeptMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        // 按照seq从小到大排序
        rootList.sort(Comparator.comparingInt(SysDepartment::getSeq));
        // 递归生成树
        transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
        return rootList;
    }

    // level:0, 0, all 0->0.1,0.2
    // level:0.1
    // level:0.2
    public void transformDeptTree(List<DepartmentLevelDto> deptLevelList, String level, Multimap<String, DepartmentLevelDto> levelDeptMap) {
        for (int i = 0; i < deptLevelList.size(); i++) {
            // 遍历该层的每个元素
            DepartmentLevelDto DepartmentLevelDto = deptLevelList.get(i);
            // 处理当前层级的数据
            String nextLevel = LevelUtil.calculateLevel(level, DepartmentLevelDto.getId());
            // 处理下一层
            List<DepartmentLevelDto> tempDeptList = (List<DepartmentLevelDto>) levelDeptMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempDeptList)) {
                // 排序
                tempDeptList.sort(Comparator.comparingInt(SysDepartment::getSeq));
                // 设置下一层部门
                DepartmentLevelDto.setSubDepartments(tempDeptList);
                // 进入到下一层处理
                transformDeptTree(tempDeptList, nextLevel, levelDeptMap);
            }
        }
    }

}
