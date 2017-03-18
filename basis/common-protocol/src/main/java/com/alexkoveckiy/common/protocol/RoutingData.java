package com.alexkoveckiy.common.protocol;

import java.io.Serializable;

/**
 * Created by alex on 08.03.17.
 */
public class RoutingData implements Serializable {
    private static final long serialVersionUID = -5850459221736408718L;

    private String userId;

    private String deviceId;

    public RoutingData(String userId, String deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
