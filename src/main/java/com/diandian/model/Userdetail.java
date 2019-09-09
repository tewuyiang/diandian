package com.diandian.model;

import java.util.Date;

public class Userdetail {
    private Integer id;

    private Integer roomdetailid;

    private Integer userid;

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