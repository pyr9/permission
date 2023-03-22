package com.pyr.permission.mail;

import com.pyr.permission.common.mail.Mail;
import com.pyr.permission.common.mail.MailUtil;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class MailUtilTest {

    @Test
    void send() {
        Mail mail = new Mail();
        mail.setSubject("测试邮件发送主题");
        mail.setMessage("邮件内容");
        Set<String> receivers = new HashSet<>();
        receivers.add("1844593053@qq.com");
        receivers.add("jeqrxnerte@iubridge.com");
        mail.setReceivers(receivers);
        MailUtil.send(mail);
    }
}