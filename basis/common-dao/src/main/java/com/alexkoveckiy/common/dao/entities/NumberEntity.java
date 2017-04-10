package com.alexkoveckiy.common.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by alex on 22.03.17.
 */
@Entity
@Table(name = "number")
public class NumberEntity extends BaseEntity {

    private static final long serialVersionUID = 3013201004069888318L;

    @Column(name = "contact_id")
    private String contactId;
    @Column(name = "number")
    private String number;

    public NumberEntity() {
    }

    public NumberEntity(String contactId, String number) {
        this.contactId = contactId;
        this.number = number;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
