package com.pyr.permission.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ResponseResult> exceptionHandle(Exception ex) { // 处理方法参数的异常类型
        log.error("throw method:【{}】,  message:【{}】", ex.getStackTrace()[0], ex.getMessage());

        return EmResponseUtils.failure(ex);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ResponseResult> handle(BaseException e) {
        log.error("throw method:【{}】, code:【{}】, message:【{}】, businessInfo:【{}】", ex.getStackTrace()[0], ex.getCode(), ex.getMessage(), ex.getBusinessInfo());
    }

}