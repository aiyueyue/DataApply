package com.ahkeli.model;

import java.util.Date;

public class UserInfo {
    private Short userId;

    private String userNo;

    private Short isNoPwd;

    private String userPassword;

    private String userName;

    private Short reloginFlag;

    private Date allowBegin;

    private Date allowEnd;

    private Date passTime;

    private Date expireTime;

    private Short validFlag;

    private Short userStatus;

    private String contactPhone;

    private Short maxErrnum;

    private String remark;

    private String userIcon;

    public Short getUserId() {
        return userId;
    }

    public void setUserId(Short userId) {
        this.userId = userId;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public Short getIsNoPwd() {
        return isNoPwd;
    }

    public void setIsNoPwd(Short isNoPwd) {
        this.isNoPwd = isNoPwd;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Short getReloginFlag() {
        return reloginFlag;
    }

    public void setReloginFlag(Short reloginFlag) {
        this.reloginFlag = reloginFlag;
    }

    public Date getAllowBegin() {
        return allowBegin;
    }

    public void setAllowBegin(Date allowBegin) {
        this.allowBegin = allowBegin;
    }

    public Date getAllowEnd() {
        return allowEnd;
    }

    public void setAllowEnd(Date allowEnd) {
        this.allowEnd = allowEnd;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Short getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Short validFlag) {
        this.validFlag = validFlag;
    }

    public Short getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Short userStatus) {
        this.userStatus = userStatus;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public Short getMaxErrnum() {
        return maxErrnum;
    }

    public void setMaxErrnum(Short maxErrnum) {
        this.maxErrnum = maxErrnum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}