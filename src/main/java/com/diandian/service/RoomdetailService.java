package com.diandian.service;

import com.diandian.model.Roomdetail;

public interface RoomdetailService {

    /**
     * 插入一条roomdetail
     * @param roomdetail
     * @return
     * @throws Exception
     */
    Integer insertRoomdetail(Roomdetail roomdetail) throws Exception;
}
