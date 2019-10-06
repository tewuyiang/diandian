package com.diandian.model;

public class Qrcode {
    private Integer id;

    private Integer roomid;

    private String uuidname;

    private String realname;

    private String savepath;

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

    public String getUuidname() {
        return uuidname;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname == null ? null : uuidname.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath == null ? null : savepath.trim();
    }
}