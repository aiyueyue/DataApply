package com.ahkeli.model;

import java.util.Date;

public class PrivilegeInfo {
    private Integer privilegeId;

    private String privilegeName;

    private Short status;

    private Integer parentPrivilege;

    private Short nodeType;

    private String functionUrl;

    private Short priOrder;

    private Date createTime;

    private String createUser;

    private String imgUrl;

    private Short imgType;

    private String remark;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName == null ? null : privilegeName.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getParentPrivilege() {
        return parentPrivilege;
    }

    public void setParentPrivilege(Integer parentPrivilege) {
        this.parentPrivilege = parentPrivilege;
    }

    public Short getNodeType() {
        return nodeType;
    }

    public void setNodeType(Short nodeType) {
        this.nodeType = nodeType;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl == null ? null : functionUrl.trim();
    }

    public Short getPriOrder() {
        return priOrder;
    }

    public void setPriOrder(Short priOrder) {
        this.priOrder = priOrder;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Short getImgType() {
        return imgType;
    }

    public void setImgType(Short imgType) {
        this.imgType = imgType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}