package com.pyr.permission.domain.department.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.pyr.permission.common.util.LevelUtil;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.base.model.BaseTreeEntity;
import com.pyr.permission.domain.department.dto.AclModuleLevelDto;
import com.pyr.permission.domain.department.dto.DepartmentLevelDto;
import com.pyr.permission.domain.department.mapper.SysDepartmentMapper;
import com.pyr.permission.domain.department.model.SysDepartment;
import com.pyr.permission.domain.role.mapper.SysAclMapper;
import com.pyr.permission.domain.role.mapper.SysAclModuleMapper;
import com.pyr.permission.domain.role.model.SysAcl;
import com.pyr.permission.domain.role.model.SysAclModule;
import com.pyr.permission.domain.role.service.SysCoreService;
import com.pyr.permission.domain.role.vo.AclVo;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysTreeService {

    @Autowired
    SysAclMapper sysAclMapper;
    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Resource
    private SysCoreService sysCoreService;

    public List<DepartmentLevelDto> deptTree() {
        List<SysDepartment> deptList = sysDepartmentMapper.getAllDept();
        List<DepartmentLevelDto> dtoList = Lists.newArrayList();
        for (SysDepartment dept : deptList) {
            DepartmentLevelDto dto = DepartmentLevelDto.adapt(dept);
            dtoList.add(dto);
        }
        return deptListToTree(dtoList);
    }

    public List<AclModuleLevelDto> aclModuleTree() {
        List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();
        List<AclModuleLevelDto> dtoList = Lists.newArrayList();
        for (SysAclModule aclModule : aclModuleList) {
            dtoList.add(AclModuleLevelDto.adapt(aclModule));
        }
        return aclModuleListToTree(dtoList);
    }

    /**
     * 1. 查询当前系统所有权限点
     * 2. 查询当前角色分配的权限点
     * 3. 如果当前角色有该权限点，返回checked=true
     * 4. <权限模块_id,权限点>做Map
     * 5. 权限模块遍历，设置该模块下的权限点（权限点根据seq排序）
     *
     * @param roleId 角色Id
     * @return 根据权限模块分类后的权限点的树状结构
     */
    public List<AclModuleLevelDto> roleTree(Long roleId) {
        List<SysAcl> allAcl = sysAclMapper.getAll();
        List<Long> aclIdsByRole = sysCoreService.getRoleAclList(roleId).stream().map(SysAcl::getId).collect(Collectors.toList());
        val aclDtoList = allAcl.stream().map(acl -> {
            AclVo aclVo = (AclVo) SysBeanUtil.convert(acl, AclVo.class);
            if (aclIdsByRole.contains(acl.getId())) {
                aclVo.setChecked(true);
            }
            // 目前所有人都可以设置所有的权限，没有存在某个人，对某些权限禁用的设计
            aclVo.setDisable(false);
            return aclVo;
        }).collect(Collectors.toList());
        return aclListToTree(aclDtoList);
    }

    private List<DepartmentLevelDto> deptListToTree(List<DepartmentLevelDto> deptLevelList) {
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
    private void transformDeptTree(List<DepartmentLevelDto> deptLevelList, String level, Multimap<String, DepartmentLevelDto> levelDeptMap) {
        for (com.pyr.permission.domain.department.dto.DepartmentLevelDto DepartmentLevelDto : deptLevelList) {
            // 遍历该层的每个元素
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

    private List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
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

    private void transformAclModuleTree(List<AclModuleLevelDto> dtoList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
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

    private List<AclModuleLevelDto> aclListToTree(List<AclVo> aclVoList) {
        if (CollectionUtils.isEmpty(aclVoList)) {
            return Lists.newArrayList();
        }
        List<AclModuleLevelDto> aclModuleLevelList = aclModuleTree();

        Multimap<Long, AclVo> moduleIdAclMap = ArrayListMultimap.create();
        for (AclVo acl : aclVoList) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleLevelList, moduleIdAclMap);
        return aclModuleLevelList;
    }

    private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelList, Multimap<Long, AclVo> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleLevelList)) {
            return;
        }
        for (AclModuleLevelDto dto : aclModuleLevelList) {
            List<AclVo> aclVoList = (List<AclVo>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclVoList)) {
                aclVoList.sort(Comparator.comparingInt(SysAcl::getSeq));
                dto.setAclList(aclVoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    public List<AclModuleLevelDto> userAclTree(long userId) {
        List<SysAcl> userAclList = sysCoreService.getUserAcls(userId);
        List<AclVo> aclVos = Lists.newArrayList();
        for (SysAcl acl : userAclList) {
            AclVo aclVo = (AclVo) SysBeanUtil.convert(acl, AclVo.class);
            aclVo.setDisable(false);
            aclVo.setChecked(true);
            aclVos.add(aclVo);
        }
        return aclListToTree(aclVos);
    }
}
