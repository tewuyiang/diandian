package com.diandian.service;

import com.diandian.model.User;
import com.diandian.model.custom.UserCustom;


public interface UserService {

    /**
     * 根据主键更新用户信息
     * @param user
     * @return
     * @throws Exception
     */
    Integer updateUserById(User user) throws Exception;


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User selectUserById(Integer id) throws Exception;


    /**
     * 插入新用户
     * @param user
     * @return
     */
    Integer insertUser(User user) throws Exception;


    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    UserCustom getUserByOpenid(String openid) throws Exception;
}
