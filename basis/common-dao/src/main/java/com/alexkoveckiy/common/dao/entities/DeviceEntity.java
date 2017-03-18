package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alex on 16.03.17.
 */
@Entity
@Table(name = "devices")
public class DeviceEntity implements EntityInterface {

    private static final long serialVersionUID = 1115505457482755907L;

    @Id
    private String id;

    private String userId;

    private String deviceId;

    private Long confirmationTime;

    public DeviceEntity() {
    }

    public DeviceEntity(String id, String userId, String deviceId, Long confirmationTime) {
        this.id = id;
        this.userId = userId;
        this.deviceId = deviceId;
        this.confirmationTime = confirmationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Long getConfirmationTime() {
        return confirmationTime;
    }

    public void setConfirmationTime(Long confirmationTime) {
        this.confirmationTime = confirmationTime;
    }
}
