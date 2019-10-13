package com.diandian.service.impl;

import com.diandian.dao.UserMapper;
import com.diandian.dao.custom.UserCustomMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.User;
import com.diandian.model.custom.RoomCustom;
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
        if (user == null || user.getId() == null || user.getNickname() == null
                || "".equals(user.getNickname())) {
            throw new ParamException("信息获取异常");
        }

        // null表示不修改
        if ("".equals(user.getWxid())) {
            user.setWxid(null);
        }
        return userMapper.updateByPrimaryKeySelective(user);
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
        if (user == null || user.getWxid() == null || "".equals(user.getWxid())
                || user.getNickname() == null || "".equals(user.getNickname())
                || user.getId() != null) {
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
        if (openid == null || "".equals(openid)) {
            throw new ParamException("信息获取异常");
        }
        return userCustomMapper.getUserByOpenid(openid);
    }


    /**
     * 根据userid查询用户创建的所有房间
     * @param userid
     * @return
     */
    @Override
    public List<RoomCustom> selectRoomsByUserId(Integer userid) throws Exception{
        if (userid == null) {
            throw new ParamException("信息获取异常");
        }
        // 用户不存在
        User user = userMapper.selectByPrimaryKey(userid);
        if (user == null) {
            return null;
        }
        return userCustomMapper.selectRoomsByUserId(userid);
    }


    /**
     * 根据userid查询用户加入的所有房间
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public List<RoomCustom> selectJoinRoomsByUserId(Integer userId) throws Exception {
        if (userId == null) {
            throw new ParamException();
        }
        // 用户不存在
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            return null;
        }
        return userCustomMapper.selectJoinRoomsByUserId(userId);
    }

}
