package com.diandian.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Userdetail {
    private Integer id;

    private Integer roomdetailid;

    private Integer userid;

    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss",timezone="GMT+8")
    private Date attendtime;

    private String presenttime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomdetailid() {
        return roomdetailid;
    }

    public void setRoomdetailid(Integer roomdetailid) {
        this.roomdetailid = roomdetailid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getAttendtime() {
        return attendtime;
    }

    public void setAttendtime(Date attendtime) {
        this.attendtime = attendtime;
    }

    public String getPresenttime() {
        return presenttime;
    }

    public void setPresenttime(String presenttime) {
        this.presenttime = presenttime == null ? null : presenttime.trim();
    }
}