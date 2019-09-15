package com.diandian.service;

import com.diandian.model.Userdetail;

public interface UserdetailService {

    /**
     * 插入一条新数据到userdetail表中
     * @param userdetail
     */
    Integer insertUserdetail(Userdetail userdetail) throws Exception;

}
