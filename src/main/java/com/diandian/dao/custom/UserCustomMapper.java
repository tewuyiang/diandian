package com.diandian.dao.custom;

import com.diandian.model.custom.UserCustom;
import org.apache.ibatis.annotations.Select;

public interface UserCustomMapper {

    /**
     * 根据openid查询用户信息
     * @param openid
     * @return
     * @throws Exception
     */
    @Select("select * from user where openid = #{openid}")
    UserCustom getUserByOpenid(String openid) throws Exception;

}
