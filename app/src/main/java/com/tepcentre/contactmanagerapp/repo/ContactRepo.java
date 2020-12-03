package com.tepcentre.contactmanagerapp.repo;

import androidx.lifecycle.LiveData;

import com.tepcentre.contactmanagerapp.database.Contact;

public interface ContactRepo {

    void createContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(long contactId);
    void getAllContacts();
    LiveData<Contact> getContact(long contactId);
}
