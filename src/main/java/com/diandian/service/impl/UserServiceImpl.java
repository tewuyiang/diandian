package com.diandian.service.impl;

import com.diandian.dao.UserMapper;
import com.diandian.model.User;
import com.diandian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer updateByPrimaryKey(User user) throws Exception {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
