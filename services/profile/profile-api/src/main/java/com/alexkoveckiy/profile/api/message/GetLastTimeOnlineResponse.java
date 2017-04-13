package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;
import com.alexkoveckiy.profile.api.dto.ProfileStatusDTO;

import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
public class GetLastTimeOnlineResponse implements ResponseData {
    private static final long serialVersionUID = 2568856631491885822L;

    List<ProfileStatusDTO> profiles;

    public GetLastTimeOnlineResponse() {
    }

    public GetLastTimeOnlineResponse(List<ProfileStatusDTO> profiles) {
        this.profiles = profiles;
    }

    public List<ProfileStatusDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<ProfileStatusDTO> profiles) {
        this.profiles = profiles;
    }
}
