package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.entities.ContactEntity;
import com.alexkoveckiy.common.dao.entities.NumberEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ContactService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.profile.api.dto.UserProfileDTO;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.message.GetContactProfilesRequest;
import com.alexkoveckiy.profile.api.message.GetContactProfilesResponse;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 22.03.17.
 */
@Component
public class GetContactProfilesRequestHandler extends ProfileRequestHandler<GetContactProfilesRequest, GetContactProfilesResponse> {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    @Override
    public String getName() {
        return "get_contact_profiles";
    }

    @Override
    protected Response<GetContactProfilesResponse> process(Request<GetContactProfilesRequest> msg) {
        List<ContactEntity> contacts = contactService.findByProfileId(msg.getRoutingData().getProfileId());
        List<UserProfileDTO> userProfileDTOS = new ArrayList<>();
        ProfileEntity profileEntity;
        for (ContactEntity contact : contacts) {
            for (NumberEntity numberEntity : contact.getNumbers()) {
                profileEntity = profileService.findByPhoneNumber(numberEntity.getNumber());
                if (profileEntity != null)
                    userProfileDTOS.add(modelMapperService.map(profileEntity, UserProfileDTO.class));
            }
        }

        return ResponseFactory.createResponse(msg, new GetContactProfilesResponse(userProfileDTOS));
    }
}
