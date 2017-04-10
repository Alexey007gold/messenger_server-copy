package com.alexkoveckiy.profile.api.dto;

/**
 * Created by alex on 23.03.17.
 */
public class ProfileStatusDTO {

    private String profileId;

    private Long lastTimeOnline;

    private boolean isOnline;

    public ProfileStatusDTO() {
    }

    public ProfileStatusDTO(String profileId, boolean isOnline, Long lastTimeOnline) {
        this.profileId = profileId;
        this.isOnline = isOnline;
        this.lastTimeOnline = lastTimeOnline;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Long getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(Long lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }
}
