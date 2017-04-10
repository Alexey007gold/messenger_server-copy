package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.profile.api.dto.MyProfileDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.message.GetMyProfileRequest;
import com.alexkoveckiy.profile.api.message.GetMyProfileResponse;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 22.03.17.
 */
@Component
public class GetMyProfileRequestHandler extends ProfileRequestHandler<GetMyProfileRequest, GetMyProfileResponse> {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_my_profile";
    }

    @Override
    protected Response<GetMyProfileResponse> process(Request<GetMyProfileRequest> msg) {
        ProfileEntity profileEntity = profileService.findOne(msg.getRoutingData().getProfileId());

        GetMyProfileResponse data = new GetMyProfileResponse(modelMapperService.map(profileEntity, MyProfileDTO.class));

        return ResponseFactory.createResponse(msg, data);
    }
}
