package com.softgroup.profile.api.message;

import com.softgroup.common.protocol.RequestData;
import com.softgroup.profile.api.DTO.ContactDTO;

import java.util.ArrayList;

/**
 * Created by alex on 26.02.17.
 */

public class ContactSyncRequest implements RequestData {

	private static final long serialVersionUID = 4895237867750981751L;

    private String token;

	private ArrayList<ContactDTO> addedContacts;

	private ArrayList<ContactDTO> removedContacts;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<ContactDTO> getAddedContacts() {
        return addedContacts;
    }

    public void setAddedContacts(ArrayList<ContactDTO> addedContacts) {
        this.addedContacts = addedContacts;
    }

    public ArrayList<ContactDTO> getRemovedContacts() {
        return removedContacts;
    }

    public void setRemovedContacts(ArrayList<ContactDTO> removedContacts) {
        this.removedContacts = removedContacts;
    }
}
