package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
public class GetLastTimeOnlineRequest implements RequestData {
    private static final long serialVersionUID = -7560611019748565140L;

    private List<String> profiles;

    public GetLastTimeOnlineRequest() {
    }

    public GetLastTimeOnlineRequest(List<String> profiles) {
        this.profiles = profiles;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }
}
