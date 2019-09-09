package com.diandian.service;

import com.diandian.model.User;

public interface UserService {

    Integer updateByPrimaryKey(User user) throws Exception;

    User selectUserById(Integer id);
}
