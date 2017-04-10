package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.message.SetProfileSettingsRequest;
import com.alexkoveckiy.profile.api.message.SetProfileSettingsResponse;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class SetProfileSettingsRequestHandler extends ProfileRequestHandler<SetProfileSettingsRequest, SetProfileSettingsResponse> {

    @Autowired
    private ProfileSettingsService profileSettingsService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "set_profile_settings";
    }

    @Override
    protected Response<SetProfileSettingsResponse> process(Request<SetProfileSettingsRequest> msg) {
        ProfileSettingsEntity profileSettingsEntity = profileSettingsService.findByProfileId(msg.getRoutingData().getProfileId());
        modelMapperService.map(msg.getData().getSettings(), profileSettingsEntity);
        profileSettingsService.save(profileSettingsEntity);

        return ResponseFactory.createResponse(msg, new SetProfileSettingsResponse());
    }
}
