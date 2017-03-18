package com.alexkoveckiy.common.dao.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by alex on 05.03.17.
 */

@Entity
@Table(name = "contacts")
public class ContactEntity implements Serializable {

    private static final long serialVersionUID = -7753889542769024084L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Whose contact it is
    private String user;

    //Contact name
    private String name;

    //Yeah
    private String number;

    public ContactEntity() {
    }

    public ContactEntity(String user, String name, String number) {
        this.user = user;
        this.name = name;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
