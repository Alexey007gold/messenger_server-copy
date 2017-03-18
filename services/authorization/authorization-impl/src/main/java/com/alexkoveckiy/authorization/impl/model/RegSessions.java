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
    private Map<String, RegSession> regSessionMap;

    public RegSessions() {
        Cache<String, RegSession> cache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)
                .build();
        regSessionMap = cache.asMap();
    }

    public RegSession createRegSession(String phoneNumber, String deviceID, String localeCode) {
        RegSession regSession = new RegSession(phoneNumber, deviceID, localeCode);
        regSessionMap.put(regSession.getUUID(), regSession);
        return regSession;
    }

    public RegSession takeRegSession(String uuid) {
        return regSessionMap.remove(uuid);
    }
}
