package com.alexkoveckiy.authorization.api.message;

import com.alexkoveckiy.common.protocol.ResponseData;

/**
 * Created by alex on 24.02.17.
 */
public class SmsConfirmResponse implements ResponseData {

    private String deviceToken;

    public SmsConfirmResponse() {
    }

    public SmsConfirmResponse(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
