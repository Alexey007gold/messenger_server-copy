package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by alex on 12.03.17.
 */
@Entity
@Table(name = "tokensCreationTimes")
public class TokenCreationTimeEntity implements EntityInterface {
    private static final long serialVersionUID = 3500292805326242961L;

    @Id
    private String userId;

    private String deviceId;

    private Long creationDate;

    public TokenCreationTimeEntity() {
    }

    public TokenCreationTimeEntity(String userId, String deviceId, Long creationDate) {
        this.userId = userId;
        this.deviceId = deviceId;
        this.creationDate = creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getCreationDate() {
        return creationDate;
    }
}
