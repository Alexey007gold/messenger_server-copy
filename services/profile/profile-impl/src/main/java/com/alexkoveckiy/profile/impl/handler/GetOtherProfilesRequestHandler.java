package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import com.alexkoveckiy.profile.api.message.GetOtherProfilesRequest;
import com.alexkoveckiy.profile.api.message.GetOtherProfilesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class GetOtherProfilesRequestHandler extends ProfileRequestHandler<GetOtherProfilesRequest, GetOtherProfilesResponse> {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_other_profiles";
    }

    @Override
    protected Response<GetOtherProfilesResponse> process(Request<GetOtherProfilesRequest> msg) {
        List<UserProfileDTO> userProfileDTOS = new ArrayList<>();

        for (ProfileEntity profileEntity : profileService.findAll(msg.getData().getUserId()))
            userProfileDTOS.add(modelMapperService.map(profileEntity, UserProfileDTO.class));

        return ResponseFactory.createResponse(msg, new GetOtherProfilesResponse(userProfileDTOS));
    }
}
