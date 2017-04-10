package com.alexkoveckiy.common.dao.repositories;

import com.alexkoveckiy.common.dao.entities.DeviceEntity;

/**
 * Created by alex on 16.03.17.
 */
public interface DeviceRepository extends BaseRepository<DeviceEntity> {
    DeviceEntity findByProfileIdAndDeviceId(String profileId, String deviceId);
}
