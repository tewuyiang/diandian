package com.diandian.constants;

/**
 * 保存考勤过程中的常量
 */
public interface AttConstant {

    /**
     * 每次发送距离间隔的时间，单位为秒
     */
    Integer INTEVAL_TIME = 30;


    /**
     * 连接
     */
    String CONNECT = "connect";


    /**
     * 开始
     */
    String START = "start";


    /**
     * 结束
     */
    String END = "end";


    /**
     * 接收到的数据类型为位置
     */
    String LOCATION = "location";


    /**
     * 状态为成功
     */
    String SUCCESS = "success";


    /**
     * 状态为失败
     */
    String FAILURE = "failure";


    /**
     * 状态为错误
     */
    String ERROR = "error";
}
