package com.pyr.permission.util;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        HashSet<String> sets = Sets.newHashSet("/sys/user/noAuth.page", "/login.html", "/sith-mes/swagger-ui.html", "/sith-mes/webjars/springfox-swagger-ui/",
                "/sith-mes/swagger-resources", "/sith-mes/csrf", "/em-admin/sso/", "/em-admin/static/", "/em-admin/emMenu/user-acl",
                "/em-admin/emMenu/user-acl", "/em-admin/emAttachment/upload", "/em-admin/emAttachment/upload", "/em-admin/emAttachment/download",
                "/em-admin/emAclUser/face/download", "/em-admin/em-stomp", "/em-admin/emAdmNotification", "/report-service/report/reportTemplate/testSendToRoles",
                "/sith-mes/wx/signature", "/sith-mes/sithMesMstLocalStorage/create", "/sith-mes/sithMesPrdLearning/create", "/sith-mes/sithMesPrdLearning/file",
                "/em-admin/emAclRoleUser/isRoleById", "/em-admin/emAclRoleUser/isRoleByCode", "/em-admin/emAclRoleUser/getUserByRoleCode",
                "/em-archive/emArchBasicData/findByBdaCode", "/em-archive/emArchBasicData/findById", "/sith-mes/sithMesTpm", "/em-admin/emAdmSysOperateLog/clearSysOperateLogs");


        Set<String> exclusionUrlSet = new HashSet<>();
        exclusionUrlSet.add("/sys/user/noAuth.page");
        exclusionUrlSet.add("/login.html");
        exclusionUrlSet.add("/admin/index.page");
        //swagger，添加了还是出不来。。。
//        exclusionUrlSet.add("/em-admin/swagger-ui.html");
        exclusionUrlSet.add("/sith-mes/swagger-ui.html");
        exclusionUrlSet.add("/sith-mes/webjars/springfox-swagger-ui/");
        exclusionUrlSet.add("/sith-mes/swagger-resources");
        exclusionUrlSet.add("/sith-mes/csrf");
        exclusionUrlSet.add("/em-admin/sso/");
        exclusionUrlSet.add("/em-admin/static/");
        exclusionUrlSet.add("/em-admin/emMenu/user-acl");
        //上传文件接口
        exclusionUrlSet.add("/em-admin/emAttachment/upload");
        //下载文件接口
        exclusionUrlSet.add("/em-admin/emAttachment/download");
        exclusionUrlSet.add("/em-admin/emAclUser/face/download");
        //系统通知
//        exclusionUrlSet.add("/adm-service/ws-mmf-stomp/info");
        exclusionUrlSet.add("/em-admin/em-stomp");
        //todo：暂时先这样，日后应设计一套微服务之间的鉴权机制，使用在类似定时任务这种后台之间自发调用的场景上。或者通过网关，让这类调用只能在服务间开展，从外面进来的一律挡住
        exclusionUrlSet.add("/em-admin/emAdmNotification");
        exclusionUrlSet.add("/report-service/report/reportTemplate/testSendToRoles");
        //微信公众号的token验证接口
        exclusionUrlSet.add("/sith-mes/wx/signature");
        //文件上传
        exclusionUrlSet.add("/sith-mes/sithMesMstLocalStorage/create");
        exclusionUrlSet.add("/sith-mes/sithMesPrdLearning/create");
        exclusionUrlSet.add("/sith-mes/sithMesPrdLearning/file");
        //查询用户是否是该角色
        exclusionUrlSet.add("/em-admin/emAclRoleUser/isRoleById");
        exclusionUrlSet.add("/em-admin/emAclRoleUser/isRoleByCode");
        //根据角色code获取用户列表
        exclusionUrlSet.add("/em-admin/emAclRoleUser/getUserByRoleCode");
        //根据设备编码获取设备信息
        exclusionUrlSet.add("/em-archive/emArchBasicData/findByBdaCode");
        //根据设备Id获取设备信息
        exclusionUrlSet.add("/em-archive/emArchBasicData/findById");
        //机床数采
        exclusionUrlSet.add("/sith-mes/sithMesTpm");
        //清理系统日志
        exclusionUrlSet.add("/em-admin/emAdmSysOperateLog/clearSysOperateLogs");

        System.out.println(sets.size());
        System.out.println(exclusionUrlSet.removeAll(sets));
        System.out.println("exclusionUrlSet: " + exclusionUrlSet);
    }
}
