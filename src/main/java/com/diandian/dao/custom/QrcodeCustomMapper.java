package com.diandian.dao.custom;

import com.diandian.model.Qrcode;
import org.apache.ibatis.annotations.Select;

public interface QrcodeCustomMapper {

    /**
     * 根据房间号获取房间id
     * @param roomId
     * @return
     * @throws Exception
     */
    @Select("select * from qrcode where roomid = #{roomId}")
    Qrcode selectQrcodeByRoomId(Integer roomId) throws Exception;
}
