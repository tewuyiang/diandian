package com.diandian.model.custom;

import com.diandian.model.Lists;
import com.diandian.model.Statistics;

public class StatisticsCustom extends Statistics {
    // 用户在房间中的信息
    private Lists lists;

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }
}
