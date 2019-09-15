package com.diandian.service.impl;

import com.diandian.dao.UserdetailMapper;
import com.diandian.exception.ParamException;
import com.diandian.model.Userdetail;
import com.diandian.service.UserdetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserdetailServiceImpl implements UserdetailService {

    @Autowired
    private UserdetailMapper userdetailMapper;

    /**
     * 插入一条数据到userdetail表中
     */
    @Override
    public Integer insertUserdetail(Userdetail userdetail) throws Exception {
        if (userdetail == null) {
            throw new ParamException("信息获取异常");
        }
        return userdetailMapper.insertSelective(userdetail);
    }
}
