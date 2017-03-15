package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by alex on 05.03.17.
 */
@Entity
@Table(name = "users")
public class UserEntity implements EntityInterface {

    private static final long serialVersionUID = -1019243451531277705L;

    @Id
    @GeneratedValue
    private String userId;

    private String phoneNumber;


    public UserEntity() {
    }

    public UserEntity(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
