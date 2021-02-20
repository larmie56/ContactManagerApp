package com.tepcentre.contactmanagerapp.repo;

import androidx.lifecycle.LiveData;

import com.tepcentre.contactmanagerapp.database.Contact;

import java.util.List;

import io.reactivex.Single;

public interface ContactRepo {

    void createContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(long contactId);
    LiveData<List<Contact>> getAllContacts();
    LiveData<Contact> getContact(long contactId);
    Single<Boolean> existOrInsertContact(long numberToGet, Contact contactToInsert);
    void cleanup();
}
