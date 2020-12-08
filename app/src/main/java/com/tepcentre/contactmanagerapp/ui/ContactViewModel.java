package com.tepcentre.contactmanagerapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tepcentre.contactmanagerapp.database.Contact;
import com.tepcentre.contactmanagerapp.database.ContactDao;
import com.tepcentre.contactmanagerapp.database.ContactDatabase;
import com.tepcentre.contactmanagerapp.repo.ContactRepo;
import com.tepcentre.contactmanagerapp.repo.ContactRepoImpl;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private final ContactRepo mContactRepo;

    private LiveData<List<Contact>> mContactListLiveData;
    private LiveData<Contact> mContactLiveData;

    public ContactViewModel(@NonNull Application application) {
        super(application);

        mContactRepo = new ContactRepoImpl(application);
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
        mContactListLiveData = mContactRepo.getAllContacts();
    }

    public void getContact(long contactId) {
        mContactLiveData = mContactRepo.getContact(contactId);
    }

    public LiveData<List<Contact>> getContactListLiveData() {
        return mContactListLiveData;
    }

    public LiveData<Contact> getContactLiveData() {
        return mContactLiveData;
    }
}
