package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by alex on 12.03.17.
 */
@Entity
@Table(name = "tokens")
public class TokenEntity implements EntityInterface {
    private static final long serialVersionUID = 3500292805326242961L;

    @Id
    private String phoneNumber;

    private String deviceId;

    private Long creationDate;

    public TokenEntity() {
    }

    public TokenEntity(String phoneNumber, String deviceId, Long creationDate) {
        this.phoneNumber = phoneNumber;
        this.deviceId = deviceId;
        this.creationDate = creationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getCreationDate() {
        return creationDate;
    }
}
