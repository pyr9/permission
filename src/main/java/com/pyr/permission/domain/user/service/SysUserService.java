package com.pyr.permission.domain.user.service;

import com.google.common.base.Preconditions;
import com.pyr.permission.common.exception.ParamException;
import com.pyr.permission.common.page.PageQuery;
import com.pyr.permission.common.page.PageResult;
import com.pyr.permission.common.util.BeanValidator;
import com.pyr.permission.common.util.MD5Util;
import com.pyr.permission.common.util.PasswordUtil;
import com.pyr.permission.common.util.SysBeanUtil;
import com.pyr.permission.domain.user.mapper.SysUserMapper;
import com.pyr.permission.domain.user.model.SysUser;
import com.pyr.permission.domain.user.param.UserParam;
import lombok.val;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private CuratorFramework zkClient;


    public void save(UserParam param) {
        BeanValidator.check(param);
        validateParam(param);
        String password = PasswordUtil.randomPassword();
        SysUser user = (SysUser) SysBeanUtil.convert(param, SysUser.class);
        user.setPassword(MD5Util.encrypt(password));
        // TODO: sendEmail
        sysUserMapper.insert(user);
    }

    public int insertUser(SysUser user, String token) throws Exception {
        InterProcessMutex lock = new InterProcessMutex(zkClient, "/" + token);
        val isLock = lock.acquire(30, TimeUnit.SECONDS);
        if (isLock) {
            return sysUserMapper.insert(user);
        }
        return 0;
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        validateParam(param);
        SysUser before = sysUserMapper.selectById(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = (SysUser) SysBeanUtil.convert(param, SysUser.class);
        sysUserMapper.updateById(after);
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

    public SysUser findById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    public PageResult<SysUser> pageByDepartmentId(Long deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDepartmentId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.pageByDepartmentId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}
