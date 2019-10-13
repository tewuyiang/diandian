package com.diandian.exception;

import com.diandian.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ParamException.class)
    public R paramExceptionHandler(ParamException e) {
        e.printStackTrace();
        return R.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(DataOperateException.class)
    public R DataOperateExceptionHandler(DataOperateException e) {
        e.printStackTrace();
        return R.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        e.printStackTrace();
        return R.error("发生异常!");
    }

}
