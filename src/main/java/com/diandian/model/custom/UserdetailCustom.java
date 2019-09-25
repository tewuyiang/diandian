package com.diandian.model.custom;

import com.diandian.model.Roomdetail;
import com.diandian.model.Userdetail;

public class UserdetailCustom extends Userdetail {

    private Roomdetail roomdetail;

    public Roomdetail getRoomdetail() {
        return roomdetail;
    }

    public void setRoomdetail(Roomdetail roomdetail) {
        this.roomdetail = roomdetail;
    }
}
