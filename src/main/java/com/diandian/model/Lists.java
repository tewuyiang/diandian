package com.diandian.model;

public class Lists {
    private Integer id;

    private Integer roomid;

    private Integer userid;

    private String remarkname;

    private Short del;

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

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname == null ? null : remarkname.trim();
    }

    public Short getDel() {
        return del;
    }

    public void setDel(Short del) {
        this.del = del;
    }

    @Override
    public String toString() {
        return "Lists{" +
                "id=" + id +
                ", roomid=" + roomid +
                ", userid=" + userid +
                ", remarkname='" + remarkname + '\'' +
                ", del=" + del +
                '}';
    }
}