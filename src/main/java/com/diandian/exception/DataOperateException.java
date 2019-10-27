package com.diandian.exception;

/**
 * 操作数据库时结果异常
 */
public class DataOperateException extends Exception{
    // 错误信息
    private String message = "数据操作异常！";
    private Integer code = -1;

    public DataOperateException() {
    }

    public DataOperateException(String message) {
        this.message = message;
    }

    public DataOperateException(Integer code, String message) {
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
