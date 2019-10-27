package com.diandian.exception;

/**
 * 参数异常
 */
public class ParamException extends RuntimeException {

    // 错误信息
    private String message = "信息获取异常！";
    private Integer code = -1;

    public ParamException() {
    }

    public ParamException(String message) {
        this.message = message;
    }

    public ParamException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
