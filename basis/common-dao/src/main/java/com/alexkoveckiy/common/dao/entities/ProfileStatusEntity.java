package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 23.03.17.
 */
@Entity
@Table(name = "profile_status")
public class ProfileStatusEntity extends BaseEntity {
    private static final long serialVersionUID = 3495456481344801989L;

    @Column(name = "profile_id")
    private String profileId;
    @Column(name = "last_time_online")
    private Long lastTimeOnline;

    public ProfileStatusEntity() {
    }

    public ProfileStatusEntity(String profileId, Long lastTimeOnline) {
        this.profileId = profileId;
        this.lastTimeOnline = lastTimeOnline;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Long getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(Long lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }
}
