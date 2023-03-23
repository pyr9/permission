package com.pyr.permission.domain.department.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.LevelUtil;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.department.dto.DepartmentLevelDto;
import com.pyr.permission.domain.department.mapper.SysDepartmentMapper;
import com.pyr.permission.domain.department.model.SysDepartment;
import com.pyr.permission.domain.department.param.SysDepartmentParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDepartmentService {

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    public void save(SysDepartmentParam param) {
        validateParam(param);
        SysDepartment dept = buildSysDepartment(param);
        sysDepartmentMapper.insertSelective(dept);
    }

    @Transactional
    public void update(SysDepartmentParam param) {
        validateParam(param);
        SysDepartment before = sysDepartmentMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        SysDepartment after = buildSysDepartment(param);
        updateWithChild(before, after);
    }

    private SysDepartment buildSysDepartment(SysDepartmentParam param) {
        SysDepartment after = (SysDepartment) SysBeanUtil.convert(param, SysDepartment.class);
        String parentLevel = getLevel(param.getParentId());
        after.setLevel(LevelUtil.calculateLevel(parentLevel, param.getParentId()));
        return after;
    }

    public List<DepartmentLevelDto> deptTree() {
        return sysTreeService.deptTree();
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDepartmentMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer departmentId) {
        SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentId);
        if (sysDepartment == null) {
            return null;
        }
        return sysDepartment.getLevel();
    }

    private void updateWithChild(SysDepartment before, SysDepartment after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysDepartment> deptList = sysDepartmentMapper.getChildDeptListByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDepartment dept : deptList) {
                    String level = dept.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDepartmentMapper.batchUpdateLevel(deptList);
            }
        }
        sysDepartmentMapper.updateByPrimaryKey(after);
    }

    private void validateParam(SysDepartmentParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
    }
}
