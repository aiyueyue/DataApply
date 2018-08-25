package com.ahkeli.model;

import java.math.BigDecimal;
import java.util.Date;

public class TabelTestData {
    private Long dataId;

    private String vehicleId;

    private String vehicleLicenseId;

    private Short vehicleColoeId;

    private Long vehicleWarnSign;

    private Short vehicleDeviceStatus;

    private Short vehicleServiceStatus;

    private BigDecimal locationLong;

    private BigDecimal locationLati;

    private Short locationHeight;

    private Short averageSpeed;

    private Short vehicleDirection;

    private Short vehicleDirectionAngle;

    private Date positionTime;

    private Date insertTime;

    private Date dealTime;

    private Short isDeal;

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId == null ? null : vehicleId.trim();
    }

    public String getVehicleLicenseId() {
        return vehicleLicenseId;
    }

    public void setVehicleLicenseId(String vehicleLicenseId) {
        this.vehicleLicenseId = vehicleLicenseId == null ? null : vehicleLicenseId.trim();
    }

    public Short getVehicleColoeId() {
        return vehicleColoeId;
    }

    public void setVehicleColoeId(Short vehicleColoeId) {
        this.vehicleColoeId = vehicleColoeId;
    }

    public Long getVehicleWarnSign() {
        return vehicleWarnSign;
    }

    public void setVehicleWarnSign(Long vehicleWarnSign) {
        this.vehicleWarnSign = vehicleWarnSign;
    }

    public Short getVehicleDeviceStatus() {
        return vehicleDeviceStatus;
    }

    public void setVehicleDeviceStatus(Short vehicleDeviceStatus) {
        this.vehicleDeviceStatus = vehicleDeviceStatus;
    }

    public Short getVehicleServiceStatus() {
        return vehicleServiceStatus;
    }

    public void setVehicleServiceStatus(Short vehicleServiceStatus) {
        this.vehicleServiceStatus = vehicleServiceStatus;
    }

    public BigDecimal getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(BigDecimal locationLong) {
        this.locationLong = locationLong;
    }

    public BigDecimal getLocationLati() {
        return locationLati;
    }

    public void setLocationLati(BigDecimal locationLati) {
        this.locationLati = locationLati;
    }

    public Short getLocationHeight() {
        return locationHeight;
    }

    public void setLocationHeight(Short locationHeight) {
        this.locationHeight = locationHeight;
    }

    public Short getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(Short averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public Short getVehicleDirection() {
        return vehicleDirection;
    }

    public void setVehicleDirection(Short vehicleDirection) {
        this.vehicleDirection = vehicleDirection;
    }

    public Short getVehicleDirectionAngle() {
        return vehicleDirectionAngle;
    }

    public void setVehicleDirectionAngle(Short vehicleDirectionAngle) {
        this.vehicleDirectionAngle = vehicleDirectionAngle;
    }

    public Date getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(Date positionTime) {
        this.positionTime = positionTime;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Short getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Short isDeal) {
        this.isDeal = isDeal;
    }
}