package com.diandian.model.custom;

import com.diandian.model.Lists;
import com.diandian.model.Roomdetail;
import com.diandian.model.Singledetail;

public class SingledetailCustom extends Singledetail {
    private Lists lists;
    private Roomdetail roomdetail;

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }

    public Roomdetail getRoomdetail() {
        return roomdetail;
    }

    public void setRoomdetail(Roomdetail roomdetail) {
        this.roomdetail = roomdetail;
    }
}
