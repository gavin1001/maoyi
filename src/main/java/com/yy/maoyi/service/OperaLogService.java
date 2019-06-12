package com.yy.maoyi.service;/**
 * Function: <br/>
 * REASON: <br/>
 * VERSION: 4.0
 *
 * @Auther: zhangyang
 * @Date: 2019/5/28.
 */

import com.yy.maoyi.dao.OperaLogDao;
import com.yy.maoyi.entity.OperaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * File Name:OperaLogService  
 * @Description TODO
 * Date:2019/5/28 20:41  
 * @author zhangyang
 * @Version 4.0
 * Copyright (c) 2019,  All Rights Reserved.  
 */
@Service
public class OperaLogService {

    @Autowired
    private OperaLogDao operaLogDao;

    public void add(OperaLog o){
        operaLogDao.add(o);
    }

}
