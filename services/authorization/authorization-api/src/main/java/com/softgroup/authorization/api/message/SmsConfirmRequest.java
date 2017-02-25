package com.softgroup.authorization.api.message;

import com.softgroup.common.protocol.RequestData;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 24.02.17.
 */

@Component
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
