package com.diandian.model;

import java.util.Date;

public class Msgtype {
    private Integer id;

    private Integer type;

    private Date sendtime;

    private Integer senduser;

    private Integer receiveuser;

    private Integer isread;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getSenduser() {
        return senduser;
    }

    public void setSenduser(Integer senduser) {
        this.senduser = senduser;
    }

    public Integer getReceiveuser() {
        return receiveuser;
    }

    public void setReceiveuser(Integer receiveuser) {
        this.receiveuser = receiveuser;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }
}