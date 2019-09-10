package com.diandian.exception;

/**
 * 参数异常
 */
public class ParamException extends RuntimeException {

    // 错误信息
    private String message;
    private Integer code;

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
}
