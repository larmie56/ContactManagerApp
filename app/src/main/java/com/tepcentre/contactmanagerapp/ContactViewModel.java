package com.tepcentre.contactmanagerapp;

import androidx.lifecycle.ViewModel;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.repo.ContactRepo;

public class ContactViewModel extends ViewModel {
    private final ContactRepo mContactRepo;

    public ContactViewModel(ContactRepo contactRepo) {
        mContactRepo = contactRepo;
    }

    public void insertContact(Contact contact) {
        mContactRepo.createContact(contact);
    }

    public void updateContact(Contact contact) {
        mContactRepo.updateContact(contact);
    }

    public void deleteContact(long contactId) {
        mContactRepo.deleteContact(contactId);
    }

    public void getAllContacts() {
        mContactRepo.getAllContacts();
    }
}
