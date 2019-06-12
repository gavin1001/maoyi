package com.yy.maoyi.service;/**
 * Function: <br/>
 * REASON: <br/>
 * VERSION: 4.0
 *
 * @Auther: zhangyang
 * @Date: 2019/5/28.
 */

import com.yy.maoyi.entity.User;
import com.yy.maoyi.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * File Name:UserService  
 * @Description TODO
 * Date:2019/5/28 19:01  
 * @author zhangyang
 * @Version 4.0
 * Copyright (c) 2019,  All Rights Reserved.  
 */

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getUserByEntity(User user){
        return userDao.getUserByEntity(user);
    }

}
