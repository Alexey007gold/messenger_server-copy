package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
public class GetOtherProfilesResponse implements ResponseData {
    private static final long serialVersionUID = 4249129958229260625L;

    private List<UserProfileDTO> profiles;

    public GetOtherProfilesResponse() {
    }

    public GetOtherProfilesResponse(List<UserProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public List<UserProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfileDTO> profiles) {
        this.profiles = profiles;
    }
}
