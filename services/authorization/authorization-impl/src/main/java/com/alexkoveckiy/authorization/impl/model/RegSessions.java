package com.alexkoveckiy.authorization.impl.model;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class RegSessions {
    private Map<String, RegSession> uuidRegSessionMap;
    private Map<String, RegSession> deviceIdRegSessionMap;

    public RegSessions() {
        Cache<String, RegSession> cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();
        uuidRegSessionMap = cache.asMap();
        cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.MINUTES).build();
        deviceIdRegSessionMap = cache.asMap();
    }

    public RegSession createRegSession(String phoneNumber, String deviceId, String localeCode) {
        RegSession regSession = new RegSession(phoneNumber, deviceId, localeCode);
        uuidRegSessionMap.put(regSession.getUUID(), regSession);
        deviceIdRegSessionMap.put(deviceId, regSession);
        return regSession;
    }

    public RegSession takeRegSession(String uuid) {
        RegSession regSession = uuidRegSessionMap.remove(uuid);
        if (regSession != null)
            deviceIdRegSessionMap.remove(regSession.getDeviceId());
        return regSession;
    }

    public boolean exists(String phoneNumber, String deviceId) {
        RegSession regSession = deviceIdRegSessionMap.get(deviceId);
        return regSession != null && regSession.getPhoneNumber().equals(phoneNumber);
    }
}
