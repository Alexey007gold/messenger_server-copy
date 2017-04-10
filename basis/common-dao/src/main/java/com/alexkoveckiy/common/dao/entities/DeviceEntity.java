package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 16.03.17.
 */
@Entity
@Table(name = "device")
public class DeviceEntity extends BaseEntity {

    private static final long serialVersionUID = 1115505457482755907L;

    @Column(name = "profile_id")
    private String profileId;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "confirmation_time")
    private Long confirmationTime;

    public DeviceEntity() {
    }

    public DeviceEntity(String profileId, String deviceId, Long confirmationTime) {
        this.profileId = profileId;
        this.deviceId = deviceId;
        this.confirmationTime = confirmationTime;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
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
