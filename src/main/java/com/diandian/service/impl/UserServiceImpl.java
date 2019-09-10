package com.diandian.service.impl;

import com.diandian.dao.UserMapper;
import com.diandian.dao.custom.UserCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.User;
import com.diandian.model.custom.UserCustom;
import com.diandian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCustomMapper userCustomMapper;


    /**
     * 根据主键id更新用户信息
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateUserById(User user) throws Exception {
        if (user == null || user.getId() == null) {
            throw new ParamException("信息获取异常");
        }
        return userMapper.updateByPrimaryKey(user);
    }


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public User selectUserById(Integer id) {
        if (id == null) {
            throw new ParamException("信息获取异常");
        }
        return userMapper.selectByPrimaryKey(id);
    }


    /**
     * 插入一个新用户
     * @param user
     * @return
     */
    @Override
    public Integer insertUser(User user) {
        if (user == null) {
            throw new ParamException("信息获取异常");
        }
        return userMapper.insertSelective(user);
    }


    /**
     * 根据openid查询用户
     * @param openid
     * @return
     * @throws Exception
     */
    @Override
    public UserCustom getUserByOpenid(String openid) throws Exception {
        if (openid == null || openid.equals("")) {
            throw new ParamException("信息获取异常");
        }
        return userCustomMapper.getUserByOpenid(openid);
    }

}
