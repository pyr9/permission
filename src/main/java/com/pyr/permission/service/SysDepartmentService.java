package com.pyr.permission.service;

import com.pyr.permission.dao.SysDepartmentMapper;
import com.pyr.permission.exception.ParamException;
import com.pyr.permission.model.SysDepartment;
import com.pyr.permission.param.SysDepartmentParam;
import com.pyr.permission.util.BeanValidator;
import com.pyr.permission.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysDepartmentService {

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    public void save(SysDepartmentParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDepartment dept = SysDepartment.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();

        String parentLevel = getLevel(param.getParentId());
        dept.setLevel(LevelUtil.calculateLevel(parentLevel, param.getParentId()));
        // TODO: 2023/3/12  
        dept.setCreator("admin");
        // TODO: 2023/3/12  
        dept.setCreatorip("127.0.0.1");
        dept.setCreateTime(new Date());
        sysDepartmentMapper.insertSelective(dept);
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
}
