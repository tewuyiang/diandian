package com.diandian.dao.custom;

import com.diandian.model.custom.RoomdetailCustom;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * 根据房间号删除房间考勤记录
     * @param roomId
     * @return
     * @throws Exception
     */
    @Delete("DELETE FROM roomdetail WHERE roomid=#{roomId}")
    Integer deleteByRoomId(Integer roomId) throws Exception;
}
