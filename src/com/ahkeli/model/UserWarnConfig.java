package com.ahkeli.model;

import java.util.Date;

/**
 * Created by wangliang on 2015/12/15.
 */
public class UserWarnConfig {
    private Short userId;
    private int messageWay;
    private Date recordTime;
    private int isValid;
    private String recordUser;

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getMessageWay() {
        return messageWay;
    }

    public void setMessageWay(int messageWay) {
        this.messageWay = messageWay;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(String recordUser) {
        this.recordUser = recordUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }
}
