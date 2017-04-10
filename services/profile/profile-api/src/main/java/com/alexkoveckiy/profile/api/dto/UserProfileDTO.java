package com.alexkoveckiy.profile.api.dto;

import org.springframework.stereotype.Component;

/**
 * Created by alex on 22.03.17.
 */
@Component
public class UserProfileDTO {

    private String id;

    private String phoneNumber;

    private Long createDateTime;

    private Long updateDateTime;

    private String avatarUri;

    private String name;

    private String status;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String id, String phoneNumber, Long createDateTime, Long updateDateTime, String avatarUri, String name, String status) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
        this.avatarUri = avatarUri;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Long createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Long getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Long updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
