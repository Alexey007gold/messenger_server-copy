package com.alexkoveckiy.messenger.impl.service;

import com.alexkoveckiy.common.dao.entities.ConversationEntity;
import com.alexkoveckiy.common.dao.entities.ConversationMemberEntity;
import com.alexkoveckiy.common.dao.entities.ProfileEntity;
import com.alexkoveckiy.common.dao.service.ConversationMemberService;
import com.alexkoveckiy.common.dao.service.ConversationSettingsService;
import com.alexkoveckiy.common.dao.service.ProfileService;
import com.alexkoveckiy.common.modelmapper.ModelMapperService;
import com.alexkoveckiy.messenger.api.dto.ConversationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alex on 01.04.17.
 */
@Component
public class ConversationDTOService {

    @Autowired
    private ConversationMemberService conversationMemberService;

    @Autowired
    private ConversationSettingsService conversationSettingsService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ModelMapperService modelMapperService;

    public ConversationDTO getConversationDTO(String profileId, ConversationEntity conversationEntity) {
        ConversationDTO conversationDTO;
        conversationDTO = new ConversationDTO();
        modelMapperService.map(conversationSettingsService.findByConversationId(conversationEntity.getId()), conversationDTO);
        modelMapperService.map(conversationEntity, conversationDTO);

        if (conversationDTO.getName() == null) {
            for (ConversationMemberEntity memberEntity : conversationMemberService.findByConversationId(conversationEntity.getId())) {
                if (!memberEntity.getProfileId().equals(profileId)) {
                    ProfileEntity profileEntity = profileService.findOne(memberEntity.getProfileId());
                    if (profileEntity.getName() != null && !profileEntity.getName().isEmpty())
                        conversationDTO.setName(profileEntity.getName());
                    else conversationDTO.setName(profileEntity.getPhoneNumber());
                }
            }
        }
        return conversationDTO;
    }
}
