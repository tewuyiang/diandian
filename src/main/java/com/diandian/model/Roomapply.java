package com.diandian.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Roomapply {
    private Integer id;

    private Integer typeid;

    private Integer roomid;

    private Integer dealresult;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dealtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public Integer getDealresult() {
        return dealresult;
    }

    public void setDealresult(Integer dealresult) {
        this.dealresult = dealresult;
    }

    public Date getDealtime() {
        return dealtime;
    }

    public void setDealtime(Date dealtime) {
        this.dealtime = dealtime;
    }
}