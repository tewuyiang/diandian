package com.diandian.model.custom;

import com.diandian.model.Roomapply;

public class RoomapplyCustom extends Roomapply {
    // 房间名称
    private String rname;
    // 房间号
    private String roomnumber;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(String roomnumber) {
        this.roomnumber = roomnumber;
    }
}
