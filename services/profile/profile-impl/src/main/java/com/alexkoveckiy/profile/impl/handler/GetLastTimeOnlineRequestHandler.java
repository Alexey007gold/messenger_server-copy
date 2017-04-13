package com.alexkoveckiy.profile.impl.handler;

import com.alexkoveckiy.common.dao.service.ProfileSettingsService;
import com.alexkoveckiy.common.isonline.IsOnlineService;
import com.alexkoveckiy.common.protocol.Request;
import com.alexkoveckiy.common.protocol.Response;
import com.alexkoveckiy.common.protocol.ResponseFactory;
import com.alexkoveckiy.profile.api.dto.ProfileStatusDTO;
import com.alexkoveckiy.profile.api.handler.ProfileRequestHandler;
import com.alexkoveckiy.profile.api.message.GetLastTimeOnlineRequest;
import com.alexkoveckiy.profile.api.message.GetLastTimeOnlineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 23.03.17.
 */
@Component
public class GetLastTimeOnlineRequestHandler extends ProfileRequestHandler<GetLastTimeOnlineRequest, GetLastTimeOnlineResponse> {

    @Autowired
    private ProfileSettingsService profileSettingsService;

    @Autowired
    private IsOnlineService isOnlineService;

    @Override
    public String getName() {
        return "get_last_time_online";
    }

    @Override
    protected Response<GetLastTimeOnlineResponse> process(Request<GetLastTimeOnlineRequest> msg) {
        List<ProfileStatusDTO> profileStatusDTOS = new ArrayList<>();
        ProfileStatusDTO profileStatusDTO;

        for (String profileId : msg.getData().getProfiles()) {
            //if user wants to share his online status
            if (profileSettingsService.findByProfileId(profileId).isShareOnlineStatus()) {
                profileStatusDTO = new ProfileStatusDTO();
                profileStatusDTO.setProfileId(profileId);
                profileStatusDTO.setOnline(isOnlineService.isOnline(profileId));
                if (!profileStatusDTO.isOnline())
                    profileStatusDTO.setLastTimeOnline(isOnlineService.getLastTimeOnline(profileId));
                profileStatusDTOS.add(profileStatusDTO);
            }
        }

        return ResponseFactory.createResponse(msg, new GetLastTimeOnlineResponse(profileStatusDTOS));
    }
}
