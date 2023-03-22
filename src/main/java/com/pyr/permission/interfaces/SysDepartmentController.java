package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.domain.department.dto.DepartmentLevelDto;
import com.pyr.permission.domain.department.param.SysDepartmentParam;
import com.pyr.permission.domain.department.service.SysDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/sys/department")
@Slf4j
@Controller
public class SysDepartmentController {

    @Autowired
    private SysDepartmentService sysDepartmentService;


    @RequestMapping("/save")
    @ResponseBody
    public ResultBody saveDept(SysDepartmentParam param) {
        sysDepartmentService.save(param);
        return ResultBody.success();
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultBody updateDept(SysDepartmentParam param) {
        sysDepartmentService.update(param);
        return ResultBody.success();
    }


    @RequestMapping("/departmentTree")
    @ResponseBody
    public ResultBody tree() {
        List<DepartmentLevelDto> departmentLevelDto = sysDepartmentService.deptTree();
        return ResultBody.success(departmentLevelDto);
    }
}
