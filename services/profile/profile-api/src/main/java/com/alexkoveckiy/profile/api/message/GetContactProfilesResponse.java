package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import com.alexkoveckiy.common.protocol.ResponseData;

import java.util.List;

/**
 * Created by alex on 22.03.17.
 */
public class GetContactProfilesResponse implements ResponseData {
    private static final long serialVersionUID = -5465744497737611393L;

    private List<UserProfileDTO> profiles;

    public GetContactProfilesResponse() {
    }

    public GetContactProfilesResponse(List<UserProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public List<UserProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<UserProfileDTO> profiles) {
        this.profiles = profiles;
    }
}
