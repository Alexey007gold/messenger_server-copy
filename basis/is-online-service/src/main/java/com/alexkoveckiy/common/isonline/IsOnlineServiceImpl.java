package com.alexkoveckiy.common.isonline;

import com.alexkoveckiy.common.wssession.WebSocketSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alex on 13.04.17.
 */
@Component
public class IsOnlineServiceImpl implements IsOnlineService {

    private Map<String, Long> storage = new HashMap<>();

    @Autowired
    private WebSocketSessionService webSocketSessionService;

    @Override
    public Boolean isOnline(String profileId) {
        return webSocketSessionService.getSession(profileId) != null ||
                System.currentTimeMillis() - storage.get(profileId) < 30000;
    }

    @Override
    public Long getLastTimeOnline(String profileId) {
        return storage.get(profileId);
    }

    @Override
    public void checkOnline(String profileId) {
        storage.put(profileId, System.currentTimeMillis());
    }
}
