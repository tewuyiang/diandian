package com.diandian.constants;

/**
 * 保存考勤过程中的常量
 */
public interface AttConstant {

    /**
     * 每次发送距离间隔的时间，单位为秒
     */
    Integer INTEVAL_TIME = 10;

    /*------------------以下为操作类型----------------------*/

    /**
     * 开始考勤
     */
    String START = "start";

    /**
     * 结束考勤
     */
    String END = "end";

    /**
     * 与学生考勤状态相关的操作
     */
    String STATUS = "status";

    /**
     * 发送位置消息
     */
    String LOCATION = "location";

    /*---------------------以下为操作状态------------------------*/

    /**
     * 操作状态为成功
     */
    Integer SUCCESS = 1;


    /**
     * 操作状态为失败
     */
    Integer FAILURE = 0;


    /**
     * 操作状态为错误
     */
    Integer ERROR = -1;

    /**
     * 已经签到过，重复操作
     */
    Integer REPEAT = 2;

    /*------------------------以下为考勤状态------------------------*/

    /**
     * 考勤状态为到达
     */
    Short ARRIVE = 1;

    /**
     * 考勤状态为迟到
     */
    Short LATE = 2;

    /**
     * 考勤状态为请假
     */
    Short LEAVE = 3;

    /**
     * 考勤状态为旷课
     */
    Short ABSENTEE = 4;
}
