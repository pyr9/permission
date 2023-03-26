package com.pyr.permission.interfaces;

import com.pyr.permission.common.ResultBody;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.domain.role.param.SysAclParam;
import com.pyr.permission.domain.role.service.SysAclService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysAclModule)表控制层
 *
 * @author makejava
 * @since 2023-03-22 18:09:38
 */
@RestController
@RequestMapping("sys/acl")
public class SysAclController {
    /**
     * 服务对象
     */
    @Resource
    private SysAclService sysAclService;

    @PostMapping("/add")
    public ResultBody add(SysAclParam param) {
        return ResultBody.success(sysAclService.insert(param));
    }


    @PutMapping("/update")
    public ResultBody edit(SysAclParam param) {
        return ResultBody.success(sysAclService.update(param));
    }

    @DeleteMapping
    public ResultBody deleteById(Integer id) {
        return ResultBody.success(sysAclService.deleteById(id));
    }

    @RequestMapping("/page")
    public ResultBody queryByAclModuleId(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return ResultBody.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

}

