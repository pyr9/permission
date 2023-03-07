package com.pyr.permission.common;

import lombok.Data;
import lombok.val;

@Data
public class ResponseResult {
    private Boolean success;
    private Object data;
    private String errorMsg;

    public ResponseResult(Boolean success) {
        this.success = success;
    }

    public static ResponseResult success( Object data, String errorMsg) {
        val responseResult = new ResponseResult(true);
        responseResult.setData(data);
        responseResult.setErrorMsg(errorMsg);
        return responseResult;
    }

    public static ResponseResult fail( String errorMsg) {
        val responseResult = new ResponseResult(false);
        responseResult.setErrorMsg(errorMsg);
        return responseResult;
    }
}
