package com.diandian.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Room {
    private Integer id;

    private String rname;

    private String roomnumber;

    private Integer userid;

    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    private Short personcount;

    private Double distance;

    private Short checked;

    private String note;

    private Short del;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber == null ? null : roomnumber.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Short getPersoncount() {
        return personcount;
    }

    public void setPersoncount(Short personcount) {
        this.personcount = personcount;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Short getChecked() {
        return checked;
    }

    public void setChecked(Short checked) {
        this.checked = checked;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Short getDel() {
        return del;
    }

    public void setDel(Short del) {
        this.del = del;
    }
}