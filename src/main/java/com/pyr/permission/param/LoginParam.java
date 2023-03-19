package com.pyr.permission.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
public class LoginParam {

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 20, message = "用户名长度需要在20个字以内")
    private String username;


    @NotBlank(message = "密码不可以为空")
    @Length(min = 1, max = 20, message = "密码长度需要在20个字以内")
    private String password;
}
