package com.diandian.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Roomdetail {
    private Integer id;

    private Integer roomid;

    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "GMT+8")
    private Date begintime;

    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endtime;

    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}