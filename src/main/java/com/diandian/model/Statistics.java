package com.diandian.model;

public class Statistics {
    private Integer id;

    private Integer roomid;

    private Integer userid;

    private Integer arrive;

    private Integer late;

    private Integer leaved;

    private Integer absentee;

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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getArrive() {
        return arrive;
    }

    public void setArrive(Integer arrive) {
        this.arrive = arrive;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Integer getLeaved() {
        return leaved;
    }

    public void setLeaved(Integer leaved) {
        this.leaved = leaved;
    }

    public Integer getAbsentee() {
        return absentee;
    }

    public void setAbsentee(Integer absentee) {
        this.absentee = absentee;
    }
}