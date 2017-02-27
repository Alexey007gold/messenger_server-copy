package com.softgroup.authorization.impl.model;

import org.jose4j.keys.AesKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class CommonData {
    private Map<String, RegSession> regSessionMap = new HashMap<>();
    public final AesKey aesKey = new AesKey("superKeyyeKrepus".getBytes());

    public RegSession createRegSession(String phoneNumber, String deviceID, String localeCode) {
        RegSession regSession = new RegSession(phoneNumber, deviceID, localeCode);
        regSessionMap.put(regSession.getUUID(), regSession);
        return regSession;
    }

    public RegSession takeRegSession(String uuid) {
        return regSessionMap.remove(uuid);
    }
}
