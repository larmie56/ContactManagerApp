package com.tepcentre.contactmanagerapp;

import androidx.lifecycle.ViewModel;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.repo.ContactRepo;

public class ContactViewModel extends ViewModel {
    private ContactRepo mContactRepo;

    public ContactViewModel(ContactRepo contactRepo) {
        mContactRepo = contactRepo;
    }

    public void insertContact(Contact contact) {
        mContactRepo.createContact(contact);
    }
}
