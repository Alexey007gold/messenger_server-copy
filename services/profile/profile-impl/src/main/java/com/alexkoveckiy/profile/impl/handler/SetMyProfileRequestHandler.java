package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.profile.api.dto.MyProfileSetDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.message.SetMyProfileRequest;
import com.alexkoveckiy.profile.api.message.SetMyProfileResponse;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 22.03.17.
 */
@Component
public class SetMyProfileRequestHandler extends ProfileRequestHandler<SetMyProfileRequest, SetMyProfileResponse> {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "set_my_profile";
    }

    @Override
    protected Response<SetMyProfileResponse> process(Request<SetMyProfileRequest> msg) {
        ProfileEntity profileEntity = profileService.findOne(msg.getRoutingData().getProfileId());
        MyProfileSetDTO myProfileSetDTO = msg.getData().getProfile();
        modelMapperService.map(myProfileSetDTO, profileEntity);
        profileEntity.setUpdateDateTime(System.currentTimeMillis());
        profileService.save(profileEntity);

        return ResponseFactory.createResponse(msg, new SetMyProfileResponse());
    }
}
