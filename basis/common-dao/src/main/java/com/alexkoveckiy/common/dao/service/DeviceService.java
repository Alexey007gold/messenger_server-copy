package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.repositories.DeviceRepository;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 16.03.17.
 */

@Component
public class DeviceService extends BaseService<DeviceEntity, DeviceRepository> {
    public DeviceEntity findByProfileIdAndDeviceId(String profileId, String deviceId) {
        return repository.findByProfileIdAndDeviceId(profileId, deviceId);
    }
}
