package com.pyr.permission.util;

import com.pyr.permission.common.util.BeanValidator;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BeanValidatorTest {

    @Test
    public void validateObject() {
        val user = new User();
        val validateResult = BeanValidator.validateObject(user);
        validateResult.forEach((k, v) -> {
            System.out.print(k + "=" + v + " ");
        });
    }

    @Test
    public void check() {
        val user = new User();
        BeanValidator.check(user);
    }


    @Getter
    @Setter
    class User {
        @NotBlank
        private String msg;

        @NotNull
        @Max(value = 10, message = "id不能大于10")
        @Min(value = 10, message = "id不能小于1")
        private Integer id;
    }
}