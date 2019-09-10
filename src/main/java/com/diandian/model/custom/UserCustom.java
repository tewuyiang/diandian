package com.diandian.model.custom;

import com.diandian.model.Lists;
import com.diandian.model.User;

/**
 * User类的扩展类
 */
public class UserCustom extends User {

    private Lists lists;

    public Lists getLists() {
        return lists;
    }

    public void setLists(Lists lists) {
        this.lists = lists;
    }
}
