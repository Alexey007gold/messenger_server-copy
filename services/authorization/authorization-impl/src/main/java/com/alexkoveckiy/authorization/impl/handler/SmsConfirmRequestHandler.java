package com.alexkoveckiy.authorization.impl.handler;

import com.alexkoveckiy.authorization.api.handler.AuthorizationRequestHandler;
import com.alexkoveckiy.authorization.api.message.SmsConfirmRequest;
import com.alexkoveckiy.authorization.api.message.SmsConfirmResponse;
import com.alexkoveckiy.authorization.impl.model.RegSession;
import com.alexkoveckiy.authorization.impl.model.RegSessions;
import com.alexkoveckiy.common.dao.entities.DeviceEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.entities.ProfileStatusEntity;
import com.alexkoveckiy.common.dao.service.DeviceService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.dao.service.ProfileStatusService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.common.token.api.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.alexkoveckiy.common.protocol.ResponseFactory.Status.FORBIDDEN;

/**
 * Created by alex on 24.02.17.
 */

@Component
public class SmsConfirmRequestHandler extends AuthorizationRequestHandler<SmsConfirmRequest, SmsConfirmResponse> {

    @Autowired
    private RegSessions regSessions;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProfileSettingsService profileSettingsService;

    @Autowired
    private ProfileStatusService profileStatusService;

    @Override
    public String getName() {
        return "sms_confirm";
    }

    @Override
    public Response<SmsConfirmResponse> process(Request<SmsConfirmRequest> msg) throws Exception {
        RegSession regSession = regSessions.takeRegSession(msg.getData().getRegistrationRequestUuid());
        if (regSession == null || regSession.getAuthCode() != msg.getData().getAuthCode())
            return ResponseFactory.createResponse(msg, FORBIDDEN);

        ProfileEntity profileEntity = profileService.findByPhoneNumber(regSession.getPhoneNumber());
        if (profileEntity == null) {
            long time = System.currentTimeMillis();
            profileEntity = profileService.save(new ProfileEntity(regSession.getPhoneNumber(),
                    time, time, null, null, null));
            profileSettingsService.save(new ProfileSettingsEntity(profileEntity.getId(), true, true));
            profileStatusService.save(new ProfileStatusEntity(profileEntity.getId(), null));
        }
        String deviceToken = tokenHandler.createDeviceToken(profileEntity.getId(), regSession.getDeviceId());
        DeviceEntity deviceEntity = deviceService.findByProfileIdAndDeviceId(profileEntity.getId(), regSession.getDeviceId());
        if (deviceEntity == null)
            deviceEntity = new DeviceEntity(profileEntity.getId(), regSession.getDeviceId(), null);
        deviceEntity.setConfirmationTime(tokenHandler.getClaimsFromDeviceToken(deviceToken).getIssuedAt().getValueInMillis());
        deviceService.save(deviceEntity);

        return ResponseFactory.createResponse(msg, new SmsConfirmResponse(deviceToken));
    }
}
