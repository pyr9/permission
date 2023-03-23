package com.pyr.permission.domain.department.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.pyr.permission.common.util.LevelUtil;
import com.pyr.permission.domain.base.model.BaseTreeEntity;
import com.pyr.permission.domain.department.dto.AclModuleLevelDto;
import com.pyr.permission.domain.department.dto.DepartmentLevelDto;
import com.pyr.permission.domain.department.mapper.SysDepartmentMapper;
import com.pyr.permission.domain.department.model.SysDepartment;
import com.pyr.permission.domain.role.mapper.SysAclModuleMapper;
import com.pyr.permission.domain.role.model.SysAclModule;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SysTreeService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

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

    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Lists.newArrayList();
        }
        // level -> [aclmodule1, aclmodule2, ...] Map<String, List<Object>>
        Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
        List<AclModuleLevelDto> rootList = Lists.newArrayList();

        for (AclModuleLevelDto dto : dtoList) {
            levelAclModuleMap.put(dto.getLevel(), dto);
            if (LevelUtil.ROOT.equals(dto.getLevel())) {
                rootList.add(dto);
            }
        }
        rootList.sort(Comparator.comparingInt(BaseTreeEntity::getSeq));
        transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
        return rootList;
    }

    public void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
        for (AclModuleLevelDto dto : dtoList) {
            String nextLevel = LevelUtil.calculateLevel(level, dto.getId());
            List<AclModuleLevelDto> tempList = (List<AclModuleLevelDto>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                tempList.sort(Comparator.comparingInt(BaseTreeEntity::getSeq));
                dto.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        }
    }
}
