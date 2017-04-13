package com.alexkoveckiy.common.isonline;

/**
 * Created by alex on 13.04.17.
 */
public interface IsOnlineService {
    Boolean isOnline(String profileId);
    Long getLastTimeOnline(String profileId);
    void checkOnline(String profileId);
}
