package com.softgroup.authorization.impl.model;

import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.UUID;

/**
 * Created by alex on 16.02.17.
 */
public class RegSession {

    @Autowired
    private CommonData commonData;

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

    public String newDeviceToken() {
        JwtClaims claims = new JwtClaims();
        claims.setClaim("phone_number", phoneNumber);
        claims.setClaim("device_id", deviceId);
        claims.setClaim("locale_code", locale);
        claims.setClaim("auth_code", authCode);
        claims.setClaim("registration_request_uuid", uuid);

        JsonWebEncryption jwe = new JsonWebEncryption();
        jwe.setPayload(claims.toJson());
        jwe.setKey(commonData.aesKey);
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
        jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);

        try {
            return jwe.getCompactSerialization();
        } catch (JoseException e) {return null;}
    }
}
