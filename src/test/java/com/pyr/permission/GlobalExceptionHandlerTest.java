package com.pyr.permission;

import com.pyr.permission.common.exception.BizException;
import com.pyr.permission.domain.user.model.SysUser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class GlobalExceptionHandlerTest {

    @PostMapping("/user")
    public boolean insert(@RequestBody SysUser user) {
        System.out.println("开始新增...");
        //如果姓名为空就手动抛出一个自定义的异常！
        if (user.getUsername() == null) {
            throw new BizException(-1, "用户姓名不能为空！");
        }
        return true;
    }

    @PutMapping("/user")
    public boolean update(@RequestBody SysUser user) {
        System.out.println("开始更新...");
        //这里故意造成一个空指针的异常，并且不进行处理
        String str = null;
        str.equals("111");
        return true;
    }

    @DeleteMapping("/user")
    public boolean delete(@RequestBody SysUser user) {
        System.out.println("开始删除...");
        //这里故意造成一个异常，并且不进行处理
        Integer.parseInt("abc123");
        return true;
    }

    @GetMapping("/user")
    public List<SysUser> findByUser(SysUser user) {
        System.out.println("开始查询...");
        List<SysUser> userList = new ArrayList<>();
        SysUser user2 = new SysUser();
        user2.setId(1);
        user2.setUsername("xuwujing");
        userList.add(user2);
        return userList;
    }

}