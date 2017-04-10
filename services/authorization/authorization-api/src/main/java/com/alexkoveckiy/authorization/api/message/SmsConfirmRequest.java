package com.alexkoveckiy.authorization.api.message;


import com.alexkoveckiy.common.protocol.RequestData;

/**
 * Created by alex on 24.02.17.
 */

public class SmsConfirmRequest implements RequestData {

    private Integer authCode;

    private String registrationRequestUuid;

    public Integer getAuthCode() {
        return authCode;
    }

    public void setAuthCode(Integer authCode) {
        this.authCode = authCode;
    }

    public String getRegistrationRequestUuid() {
        return registrationRequestUuid;
    }

    public void setRegistrationRequestUuid(String registrationRequestUuid) {
        this.registrationRequestUuid = registrationRequestUuid;
    }
}
