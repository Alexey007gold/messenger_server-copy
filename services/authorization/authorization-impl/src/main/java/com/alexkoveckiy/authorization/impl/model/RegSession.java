package com.alexkoveckiy.authorization.impl.model;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.UUID;

/**
 * Created by alex on 16.02.17.
 */
public class RegSession {

    private String uuid;
    private long creationTime;
    private int authCode;
    private int timeOut;

    private String phoneNumber;
    private String deviceId;
    private String locale;

    public RegSession(String phoneNumber, String deviceId, String locale) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        this.uuid = UUID.randomUUID().toString();
        this.creationTime = System.currentTimeMillis();
        this.authCode = newAuthCode();
        this.timeOut = timeOut();

        this.phoneNumber = phoneNumber;
        this.deviceId = deviceId;
        this.locale = locale;
    }

    public RegSession(Object phoneNumber, Object deviceId, String locale) {
        this((String) phoneNumber, (String) deviceId, locale);
    }

    private int newAuthCode() {
        //TODO
        return 12345;
    }

    private int timeOut() {
        return 1120;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public String getUUID() {
        return uuid;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public int getAuthCode() {
        return authCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - creationTime >= timeOut * 1000;
    }
}
