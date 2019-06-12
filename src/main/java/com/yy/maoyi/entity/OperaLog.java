package com.yy.maoyi.entity;/**
 * Function: <br/>
 * REASON: <br/>
 * VERSION: 4.0
 *
 * @Auther: zhangyang
 * @Date: 2019/5/28.
 */

import java.util.Date;

/**
 * File Name:OperaLog  
 * @Description TODO
 * Date:2019/5/28 18:42  
 * @author zhangyang
 * @Version 4.0
 * Copyright (c) 2019,  All Rights Reserved.  
 */
public class OperaLog {

    private String uname;
    private Date operaDate;
    private String operaNum;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public String getOperaNum() {
        return operaNum;
    }

    public void setOperaNum(String operaNum) {
        this.operaNum = operaNum;
    }

    public Date getOperaDate() {
        return operaDate;
    }

    public void setOperaDate(Date operaDate) {
        this.operaDate = operaDate;
    }
}
