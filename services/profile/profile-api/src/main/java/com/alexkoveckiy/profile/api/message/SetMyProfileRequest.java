package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.profile.api.dto.MyProfileSetDTO;

/**
 * Created by alex on 22.03.17.
 */
public class SetMyProfileRequest implements RequestData {
    private static final long serialVersionUID = 6458645348821087462L;

    private MyProfileSetDTO profile;

    public MyProfileSetDTO getProfile() {
        return profile;
    }
}
