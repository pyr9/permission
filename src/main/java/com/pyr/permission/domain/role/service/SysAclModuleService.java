package com.pyr.permission.domain.role.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.LevelUtil;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.department.service.SysTreeService;
import com.pyr.permission.domain.role.mapper.SysAclModuleMapper;
import com.pyr.permission.domain.role.model.SysAclModule;
import com.pyr.permission.domain.role.param.SysAclModuleParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysAclModule)表服务实现类
 *
 * @author makejava
 * @since 2023-03-22 18:09:39
 */
@Service
public class SysAclModuleService {
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Autowired
    private SysTreeService sysTreeService;

    public SysAclModule queryById(Long id) {
        return this.sysAclModuleMapper.selectById(id);
    }


    public SysAclModule insert(SysAclModuleParam param) {
        validateParam(param);
        SysAclModule sysAclModule = buildSysAclModule(param);
        sysAclModuleMapper.insert(sysAclModule);
        return sysAclModule;
    }

    public SysAclModule update(SysAclModuleParam param) {
        validateParam(param);
        SysAclModule before = sysAclModuleMapper.selectById(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");
        SysAclModule after = buildSysAclModule(param);
        return updateWithChild(before, after);
    }

    /**
     * 如果部门的层级结构修改，那么它的子级的level都需要调整
     * 因为level
     *
     * @return
     */
    private SysAclModule updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            String curLevel = before.getLevel() + "." + before.getId();
            List<SysAclModule> deptList = sysAclModuleMapper.getChildDeptListByLevel(curLevel + "%");
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysAclModule dept : deptList) {
                    String level = dept.getLevel();
                    if (level.equals(curLevel) || level.indexOf(curLevel + ".") == 0) {
                        // getChildAclModuleListByLevel可能会取出多余的内容，因此需要加个判断
                        // 比如0.1* 可能取出0.1、0.1.3、0.11、0.11.3，而期望取出  0.1、0.1.3， 因此呢需要判断等于0.1或者以0.1.为前缀才满足条件
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(deptList);
            }
        }
        sysAclModuleMapper.updateById(after);
        return this.queryById(after.getId());
    }

    public boolean deleteById(Long id) {
        SysAclModule aclModule = sysAclModuleMapper.selectById(id);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除");
        if (sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            throw new ParamException("当前模块下面有子模块，无法删除");
        }
        return this.sysAclModuleMapper.deleteById(id) > 0;
    }

    private String getLevel(Long departmentId) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectById(departmentId);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

    public Object aclModuleTree() {
        return sysTreeService.aclModuleTree();
    }

    private void validateParam(SysAclModuleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
    }

    private boolean checkExist(Long parentId, String deptName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private SysAclModule buildSysAclModule(SysAclModuleParam param) {
        SysAclModule sysAclModule = (SysAclModule) SysBeanUtil.convert(param, SysAclModule.class);
        String parentLevel = getLevel(param.getParentId());
        sysAclModule.setLevel(LevelUtil.calculateLevel(parentLevel, param.getParentId()));
        return sysAclModule;
    }
}
