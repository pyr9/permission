package com.pyr.permission.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommonEnum implements BaseErrorInfoInterface {
    //数据操作错误定义
    SUCCESS(200, "成功"),
    BODY_NOT_MATCH(400, "请求的数据格式不符"),
    SIGNATURE_NOT_MATCH(401, "请求的数宇签名不匹配"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试"),
    ;
    /**
     * 错误码
     */
    private Integer resultCode;
    /**
     * 错误描述
     */
    private String resultMsg;


    @Override
    public Integer getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}