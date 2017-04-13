package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.profile.api.dto.ProfileSettingsSetDTO;

/**
 * Created by alex on 23.03.17.
 */
public class SetProfileSettingsRequest implements RequestData {
    private static final long serialVersionUID = -5915754270783308384L;

    private ProfileSettingsSetDTO settings;

    public SetProfileSettingsRequest() {
    }

    public SetProfileSettingsRequest(ProfileSettingsSetDTO settings) {
        this.settings = settings;
    }

    public ProfileSettingsSetDTO getSettings() {
        return settings;
    }

    public void setSettings(ProfileSettingsSetDTO settings) {
        this.settings = settings;
    }
}
