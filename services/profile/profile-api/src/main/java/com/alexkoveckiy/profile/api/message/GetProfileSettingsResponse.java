package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.profile.api.dto.ProfileSettingsDTO;

/**
 * Created by alex on 23.03.17.
 */
public class GetProfileSettingsResponse implements ResponseData {
    private static final long serialVersionUID = 5144009747472957029L;

    private ProfileSettingsDTO settings;

    public GetProfileSettingsResponse(ProfileSettingsDTO settings) {
        this.settings = settings;
    }

    public ProfileSettingsDTO getSettings() {
        return settings;
    }

    public void setSettings(ProfileSettingsDTO settings) {
        this.settings = settings;
    }
}
