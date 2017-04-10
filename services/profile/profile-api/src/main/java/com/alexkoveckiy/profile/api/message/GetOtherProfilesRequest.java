package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.RequestData;

import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
public class GetOtherProfilesRequest implements RequestData {
    private static final long serialVersionUID = 3507540672582269464L;

    private List<String> userId;

    public List<String> getUserId() {
        return userId;
    }
}
