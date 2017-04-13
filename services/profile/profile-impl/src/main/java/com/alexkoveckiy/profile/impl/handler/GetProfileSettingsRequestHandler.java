package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileSettingsEntity;
import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.dto.ProfileSettingsDTO;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import com.alexkoveckiy.profile.api.message.GetProfileSettingsRequest;
import com.alexkoveckiy.profile.api.message.GetProfileSettingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class GetProfileSettingsRequestHandler extends ProfileRequestHandler<GetProfileSettingsRequest, GetProfileSettingsResponse> {

    @Autowired
    private ProfileSettingsService profileSettingsService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_profile_settings";
    }

    @Override
    protected Response<GetProfileSettingsResponse> process(Request<GetProfileSettingsRequest> msg) {
        ProfileSettingsEntity profileSettingsEntity = profileSettingsService.findByProfileId(msg.getRoutingData().getProfileId());

        GetProfileSettingsResponse data = new GetProfileSettingsResponse(modelMapperService.map(profileSettingsEntity, ProfileSettingsDTO.class));

        return ResponseFactory.createResponse(msg, data);
    }
}
