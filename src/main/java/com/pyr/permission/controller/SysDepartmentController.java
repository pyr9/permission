package com.pyr.permission.controller;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.param.SysDepartmentParam;
import com.pyr.permission.service.SysDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/sys/dept")
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

}
