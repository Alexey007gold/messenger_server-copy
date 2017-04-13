package com.alexkoveckiy.profile.api.message;

import com.alexkoveckiy.common.protocol.RequestData;
import com.alexkoveckiy.profile.api.dto.ContactDTO;

import java.util.List;

/**
 * Created by alex on 26.02.17.
 */

public class ContactSyncRequest implements RequestData {

	private static final long serialVersionUID = 4895237867750981751L;

	private List<ContactDTO> addedContacts;

	private List<ContactDTO> removedContacts;

    public List<ContactDTO> getAddedContacts() {
        return addedContacts;
    }

    public void setAddedContacts(List<ContactDTO> addedContacts) {
        this.addedContacts = addedContacts;
    }

    public List<ContactDTO> getRemovedContacts() {
        return removedContacts;
    }

    public void setRemovedContacts(List<ContactDTO> removedContacts) {
        this.removedContacts = removedContacts;
    }
}
