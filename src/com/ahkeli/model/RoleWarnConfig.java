package com.ahkeli.model;

import java.util.Date;

/**
 * Created by dell on 2015/12/10.
 */
public class RoleWarnConfig {
    private Short roleId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    public String getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(String recordUser) {
        this.recordUser = recordUser;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public int getMessageWay() {
        return messageWay;
    }

    public void setMessageWay(int messageWay) {
        this.messageWay = messageWay;
    }
}
