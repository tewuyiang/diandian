package com.diandian.dao.custom;

import com.diandian.model.custom.RoomCustom;
import com.diandian.model.custom.RoomdetailCustom;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoomdetailCustomMapper {


    /**
     * 根据id查询
     * @param roomId
     * @return
     */
    @Select("select * from roomdetail where roomid = #{roomId}")
    List<RoomdetailCustom> selectRoomdetailByRoomId(Integer roomId) throws Exception;
}
