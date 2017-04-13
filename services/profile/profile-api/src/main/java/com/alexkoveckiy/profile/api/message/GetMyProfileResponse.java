package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.profile.api.dto.MyProfileDTO;

/**
 * Created by alex on 22.03.17.
 */
public class GetMyProfileResponse implements ResponseData {
    private static final long serialVersionUID = 6929862522876689546L;

    private MyProfileDTO profile;

    public GetMyProfileResponse(MyProfileDTO profile) {
        this.profile = profile;
    }

    public MyProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(MyProfileDTO profile) {
        this.profile = profile;
    }
}
