package com.alexkoveckiy.profile.api.dto;

/**
 * Created by alex on 23.03.17.
 */
public class ProfileSettingsSetDTO {

    private boolean shareOnlineStatus;

    private boolean shareSeenStatus;

    public ProfileSettingsSetDTO() {
    }

    public ProfileSettingsSetDTO(boolean shareOnlineStatus, boolean shareSeenStatus) {
        this.shareOnlineStatus = shareOnlineStatus;
        this.shareSeenStatus = shareSeenStatus;
    }

    public boolean isShareOnlineStatus() {
        return shareOnlineStatus;
    }

    public void setShareOnlineStatus(boolean shareOnlineStatus) {
        this.shareOnlineStatus = shareOnlineStatus;
    }

    public boolean isShareSeenStatus() {
        return shareSeenStatus;
    }

    public void setShareSeenStatus(boolean shareSeenStatus) {
        this.shareSeenStatus = shareSeenStatus;
    }
}
