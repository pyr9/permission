package com.pyr.permission.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.exception.ParamException;
import com.pyr.permission.mapper.SysUserMapper;
import com.pyr.permission.model.SysUser;
import com.pyr.permission.param.UserParam;
import com.pyr.permission.util.BeanValidator;
import com.pyr.permission.util.MD5Util;
import com.pyr.permission.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

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
        // TODO: 2023/3/12
        user.setCreator("admin");
        // TODO: 2023/3/12
        user.setCreatorip("127.0.0.1");
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
        after.setCreator("admin");
        // TODO: 2023/3/12
        after.setCreatorip("127.0.0.1");
        after.setCreateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    private void validateParam(UserParam param) {
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
    }
}
