package com.diandian.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Singledetail {
    private Integer id;

    private Integer roomdetailid;

    private Integer userid;

    @JsonFormat(pattern = "yy-MM-dd HH:mm", timezone = "GMT+8")
    private Date attendtime;

    private Short attendstatus;

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

    public Short getAttendstatus() {
        return attendstatus;
    }

    public void setAttendstatus(Short attendstatus) {
        this.attendstatus = attendstatus;
    }
}