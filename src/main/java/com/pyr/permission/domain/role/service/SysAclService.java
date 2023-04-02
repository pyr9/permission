package com.pyr.permission.domain.role.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.common.page.PageResult;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.role.mapper.SysAclMapper;
import com.pyr.permission.domain.role.model.SysAcl;
import com.pyr.permission.domain.role.param.SysAclParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (SysAcl)表服务实现类
 *
 * @author makejava
 * @since 2023-03-22 18:09:39
 */
@Service
public class SysAclService {
    @Resource
    private SysAclMapper sysAclMapper;


    public SysAcl insert(SysAclParam param) {
        validateParam(param);
        SysAcl SysAcl = (SysAcl) SysBeanUtil.convert(param, SysAcl.class);
        SysAcl.setCode(generateCode());
        sysAclMapper.insert(SysAcl);
        return SysAcl;
    }

    public boolean update(SysAclParam param) {
        validateParam(param);
        SysAcl before = sysAclMapper.selectById(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");
        SysAcl after = (SysAcl) SysBeanUtil.convert(param, SysAcl.class);
        return sysAclMapper.updateById(after) > 0;
    }

    public Object getPageByAclModuleId(Long aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    public boolean deleteById(Long id) {
        SysAcl aclModule = sysAclMapper.selectById(id);
        Preconditions.checkNotNull(aclModule, "待删除的权限点不存在，无法删除");
        return this.sysAclMapper.deleteById(id) > 0;
    }


    private void validateParam(SysAclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("同一权限模块下存在相同名称的权限点");
        }
    }

    private boolean checkExist(Long aclModuleId, String name, Long id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }

}
