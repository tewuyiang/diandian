package com.diandian.dao.custom;

import com.diandian.model.custom.UserdetailCustom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserdetailCustomMapper {

    /**
     * 根据房间明细号，查询房间中某一次考勤的全部用户明细
     * @param roomdetailId
     * @return
     */
    @Select("select * from userdetail where roomdetailid = #{roomdetailId}")
    List<UserdetailCustom> selectUserdetailsByRoomdetailId(Integer roomdetailId) throws Exception;


    /**
     * 查询一个用户在某个房间内的全部考勤明细
     * @param roomId
     * @param userId
     * @return
     */
    List<UserdetailCustom> selectOnStudentDetails(@Param("roomId") Integer roomId,
                                                  @Param("userId") Integer userId) throws Exception;


}
