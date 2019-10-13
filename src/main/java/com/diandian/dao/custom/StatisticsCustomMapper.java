package com.diandian.dao.custom;

import com.diandian.model.custom.StatisticsCustom;

import java.util.List;

public interface StatisticsCustomMapper {

    /**
     * 通过房间号获取某个房间的统计情况
     * @param roomId
     * @return
     */
    List<StatisticsCustom> selectByRoomId(Integer roomId) throws Exception;
}
