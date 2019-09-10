package com.diandian.dao.custom;

import com.diandian.model.custom.UserCustom;

import java.util.List;

public interface ListsCustomMapper {

    List<UserCustom> selectUserByRoomId(Integer roomID) throws Exception;
}
