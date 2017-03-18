package com.alexkoveckiy.common.dao.service;

import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.repositories.DeviceRepository;

/**
 * Created by alex on 16.03.17.
 */
public class DeviceService extends BaseService<DeviceEntity, DeviceRepository> {
    DeviceEntity findByUserIdAndDeviceId(String userId, String deviceId) {
        return repository.findByUserIdAndDeviceId(userId, deviceId);
    }
}
