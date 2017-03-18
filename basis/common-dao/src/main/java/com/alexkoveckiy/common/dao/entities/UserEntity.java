package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Entity;
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
    private String id;

    private String phoneNumber;

    public UserEntity() {
    }

    public UserEntity(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
