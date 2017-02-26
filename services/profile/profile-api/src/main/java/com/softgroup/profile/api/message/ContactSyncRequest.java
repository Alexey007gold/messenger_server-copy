package com.softgroup.profile.api.message;

import com.softgroup.profile.api.model.Contact;
import com.softgroup.common.protocol.RequestData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by alex on 26.02.17.
 */

@Component
public class ContactSyncRequest implements RequestData {

	private static final long serialVersionUID = 4895237867750981751L;

    private String token;

	private ArrayList<Contact> addedContacts;

	private ArrayList<Contact> removedContacts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<Contact> getAddedContacts() {
        return addedContacts;
    }

    public void setAddedContacts(ArrayList<Contact> addedContacts) {
        this.addedContacts = addedContacts;
    }

    public ArrayList<Contact> getRemovedContacts() {
        return removedContacts;
    }

    public void setRemovedContacts(ArrayList<Contact> removedContacts) {
        this.removedContacts = removedContacts;
    }
}
