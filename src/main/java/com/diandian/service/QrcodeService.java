package com.diandian.service;

import com.diandian.model.Room;

public interface QrcodeService {

    /**
     * 创建房间二维码
     * @param contextPath
     * @param room 房间信息
     * @return
     */
    String createRoomQrode(String contextPath, Room room) throws Exception;


    /**
     * 获取房间二维码
     * @param roomId 房间号
     * @param realPath 项目根路径
     * @return
     */
    String getRoomQrcode(Integer roomId, String realPath) throws Exception;
}
