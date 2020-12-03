package com.tepcentre.contactmanagerapp.repo;

import com.tepcentre.contactmanagerapp.database.Contact;

public interface ContactRepo {

    void createContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(long contactId);
    void getAllContacts();
}
