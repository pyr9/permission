package com.pyr.permission.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.RequestHolder;
import com.pyr.permission.exception.ParamException;
import com.pyr.permission.mapper.SysUserMapper;
import com.pyr.permission.model.SysUser;
import com.pyr.permission.page.PageQuery;
import com.pyr.permission.page.PageResult;
import com.pyr.permission.param.UserParam;
import com.pyr.permission.util.BeanValidator;
import com.pyr.permission.util.IpUtil;
import com.pyr.permission.util.MD5Util;
import com.pyr.permission.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;


    public void save(UserParam param) {
        BeanValidator.check(param);
        validateParam(param);
        String password = PasswordUtil.randomPassword();
        SysUser user = SysUser.of(param.getUsername(), param.getTelephone(), param.getMail(), MD5Util.encrypt(password),
                param.getDepartmentId(), param.getStatus(), param.getRemark());
        user.setCreatorId(RequestHolder.getCurrentUser().getId());
        user.setCreatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setCreateTime(new Date());

        // TODO: sendEmail
        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        validateParam(param);
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(param.getId()).userName(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .departmentId(param.getDepartmentId()).status(param.getStatus()).remark(param.getRemark()).build();
        after.setCreatorId(RequestHolder.getCurrentUser().getId());
        after.setCreatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setCreateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    /**
     * 检验重复：email = xxx & userId != 张三id count > 0, 说明有重复的email存在
     * 对于数据更新的场景: 如果传过来的email和old_email相等，则不需要check是否邮箱重复
     */
    private void validateParam(UserParam param) {
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
    }

    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    public SysUser findById(Integer userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    public PageResult<SysUser> pageByDepartmentId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDepartmentId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.pageByDepartmentId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}
